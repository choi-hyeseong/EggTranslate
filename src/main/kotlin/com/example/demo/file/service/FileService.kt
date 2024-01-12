package com.example.demo.file.service

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.exception.FileException
import com.example.demo.file.repository.FileRepository
import com.example.demo.file.util.FileUtil
import com.example.demo.logger
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import jakarta.transaction.Transactional
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val userService: UserService,
    private val fileRepository: FileRepository,
    @Value("\${image-path}")
    private val outPath: String
) {

    private val log = logger()

    @Transactional
    fun findFileById(id: Long): FileDTO =
        FileDTO(fileRepository
            .findById(id)
            .orElseThrow { FileException("존재하지 않는 파일입니다.") }
        )

    @Transactional
    fun getFile(userId: Long, fileId: Long): FileDTO {
        val isUserExists = userService.existUser(userId)
        val fileDto = findFileById(fileId)

        // TODO user check (admin & translator)
        if (isUserExists && fileDto.user.id == userId)
            return fileDto
        else
            throw FileException("접근할 수 있는 권한이 없습니다.")
    }

    @Transactional
    fun saveEntity(fileDTO: FileDTO): Long? {
        val user = userService.getUserEntity(fileDTO.user.id!!)
        return fileRepository.save(fileDTO.toEntity(user)).id
    }

    @Transactional
    suspend fun saveAllEntity(fileDTO: List<FileDTO>) {
        fileRepository.saveAll(fileDTO.map {
            val user = userService.getUserEntity(it.user.id!!)
            it.toEntity(user)
        }.toList())
    }

    suspend fun saveImage(userDto: UserDto, image: List<MultipartFile>): List<FileDTO> {
        //parallel image save
        return coroutineScope {
            image.map { image ->
                async {
                    saveImage(userDto, image)
                }
            }.toList().awaitAll()
        }
    }

    suspend fun saveImage(userDto: UserDto, image: MultipartFile): FileDTO {
        //parallel image save
        return coroutineScope {
            async {
                val ext = FileUtil.findExtension(image.originalFilename ?: image.name)
                val saveName = "${System.currentTimeMillis()}.$ext"
                val path = outPath.plus("\\$saveName") //value로 형식 부여받기?
                FileUtil.saveFile(image.bytes, path)
                log.info("image file saved to $path")
                FileDTO(-1, image.name, saveName, userDto, path)
            }.await()
        }
    }

}