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
import java.util.concurrent.ThreadLocalRandom
import kotlin.jvm.optionals.getOrNull

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
    suspend fun findFileEntityByIdOrNull(id: Long?): File? {
        if (id == null)
            return null
        return fileRepository.findById(id).getOrNull()
    }

    @Transactional
    suspend fun findAllFileEntityByUserIdOrNull(id: Long): List<File> = fileRepository
        .findAllByUserId(id)

    @Transactional
    suspend fun getFile(fileId: Long): Resource {
        val fileDto = findFileById(fileId)
        return FileUtil.convertFileToResource(fileDto)
    }

    @Transactional
    suspend fun saveEntity(fileDTO: FileDTO): Long? {
        val file : File = if (fileDTO.user != null)
            fileDTO.toEntity(userService.getUserEntity(fileDTO.user.id!!))
        else
            fileDTO.toEntity(null)
        return fileRepository.save(file).id
    }

    @Transactional
    suspend fun saveAllEntity(fileDTO: List<FileDTO>) {
        fileRepository.saveAll(fileDTO.map {
            if (it.user != null)
                it.toEntity(userService.getUserEntity(it.user.id!!))
            else
                it.toEntity(null)
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

    suspend fun saveImage(userDto: UserDto?, image: MultipartFile): FileDTO {
        //parallel image save
        return coroutineScope {
            async {
                val ext = FileUtil.findExtension(image.originalFilename ?: image.name)
                val saveName = "${System.currentTimeMillis()}${ThreadLocalRandom.current().nextInt(1,10000)}.$ext"
                val path = outPath.plus("/$saveName") //value로 형식 부여받기?
                FileUtil.saveFile(image.bytes, path)
                log.info("image file saved to $path")
                FileDTO(null, image.originalFilename ?: image.name, saveName, userDto, path)
            }.await()
        }
    }
    @Transactional
    suspend fun deleteFileByUserId(userId : Long) {
        val file = findAllFileEntityByUserIdOrNull(userId)
        file.forEach {
            FileUtil.deleteFile(it.savePath)
        }
        fileRepository.deleteAll(file)

    }

}