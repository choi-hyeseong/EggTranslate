package com.example.demo.file.service

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.entity.File
import com.example.demo.file.exception.FileException
import com.example.demo.file.repository.FileRepository
import com.example.demo.file.util.FileUtil
import com.example.demo.logger
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import jakarta.transaction.Transactional
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
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
    suspend fun findFileById(id: Long): FileDTO =
        FileDTO(
            findFileEntityById(id)
        )

    @Transactional
    suspend fun findFileEntityById(id: Long): File = fileRepository
        .findById(id)
        .orElseThrow { FileException("존재하지 않는 파일입니다.") }


    @Transactional
    suspend fun getFile(fileId: Long): Resource {
        val fileDto = findFileById(fileId)
        return FileUtil.convertFileToResource(fileDto)
    }

    @Transactional
    suspend fun saveEntity(fileDTO: FileDTO): Long? {
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
                FileDTO(null, image.originalFilename ?: image.name, saveName, userDto, path)
            }.await()
        }
    }

}