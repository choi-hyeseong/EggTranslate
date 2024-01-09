package com.example.demo.file

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.repository.FileRepository
import com.example.demo.file.util.FileUtil
import com.example.demo.logger
import com.example.demo.user.basic.dto.UserDto
import jakarta.transaction.Transactional
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val fileRepository: FileRepository,
    @Value("\${image-path}")
    private val outPath: String
) {

    private val log = logger()

    @Transactional
    fun saveEntity(fileDTO: FileDTO) {
        fileRepository.save(fileDTO.toEntity())
    }

    @Transactional
    suspend fun saveAllEntity(fileDTO: List<FileDTO>) {
        fileRepository.saveAll(fileDTO.map { it.toEntity() }.toList())
    }

    suspend fun saveImage(userDto: UserDto, image: List<MultipartFile>) : List<FileDTO> {
        //parallel image save
        return coroutineScope {
            image.map { image ->
                async {
                    val ext = FileUtil.findExtension(image.name)
                    val saveName = "${System.currentTimeMillis()}.$ext"
                    val path = outPath.plus("\\$saveName") //value로 형식 부여받기?
                    FileUtil.saveFile(image.bytes, path)
                    log.info("image file saved to $path")
                    FileDTO(-1, image.name, saveName, userDto, path)
                }
            }.toList().awaitAll()
        }
    }
}