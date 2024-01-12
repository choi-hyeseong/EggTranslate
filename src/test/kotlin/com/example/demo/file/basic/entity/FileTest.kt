package com.example.demo.file.basic.entity

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.repository.FileRepository
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.type.UserType
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FileTest(@Autowired private val userRepository: UserRepository, @Autowired private val fileRepository: FileRepository) {


    val user = UserDto(
        null,
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    ).toEntity()

    @Test
    @Transactional
    fun TEST_SAVE_FILE() {
        val user = userRepository.save(user)
        val fileDto = FileDTO(null, "ORIGIN", "SAVE", UserDto(user), "PATH")
        val response = assertDoesNotThrow { fileRepository.save(fileDto.toEntity(user)) }
        assertNotEquals(-1, response.id)

    }

    @Test
    @Transactional
    fun TEST_LOAD_FILE() {
        val user = userRepository.save(user)
        val fileDto = FileDTO(null, "ORIGIN", "SAVE", UserDto(user), "PATH")
        val response = assertDoesNotThrow { fileRepository.save(fileDto.toEntity(user)) }
        assertNotEquals(-1, response.id)

        val fileLoad = assertDoesNotThrow { fileRepository.findById(response.id!!).get() }
        assertEquals("ORIGIN", fileLoad.originName)
        assertEquals("SAVE", fileLoad.saveName)
        assertEquals(user.id, fileLoad.user.id)

    }

    @Test
    @Transactional
    fun TEST_GET_ALL_FILES() {
        val user = userRepository.save(user)
        val fileDto = FileDTO(null,"ORIGIN", "SAVE", UserDto(user), "PATH")
        val fileDto2 = FileDTO(null, "ORIGIN2", "SAVE2", UserDto(user), "PATH")
        assertDoesNotThrow {
            fileRepository.save(fileDto.toEntity(user))
            fileRepository.save(fileDto2.toEntity(user))
        }

        val files = fileRepository.findAllByUserId(user.id!!)
        assertEquals(2, files.size)
        assertEquals("SAVE2", files[1].saveName)



    }

    @Test
    @Transactional
    fun TEST_DELETE_ALL_FILES() {
        val user = userRepository.save(user)
        val fileDto = FileDTO(null, "ORIGIN", "SAVE", UserDto(user), "PATH")
        val fileDto2 = FileDTO(null, "ORIGIN2", "SAVE2", UserDto(user), "PATH")
        assertDoesNotThrow {
            fileRepository.save(fileDto.toEntity(user))
            fileRepository.save(fileDto2.toEntity(user))
        }

        val files = fileRepository.findAllByUserId(user.id!!)

        fileRepository.deleteAllByUserId(user.id!!) //추후 양방향으로 변경해서 cascade 되게
        userRepository.delete(user)

        assertFalse(fileRepository.existsFileById(files[0].id!!))
        assertFalse(fileRepository.existsFileById(files[1].id!!))
    }


}