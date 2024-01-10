package com.example.demo.signup.validation

import com.example.demo.user.basic.repository.UserRepository
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import lombok.RequiredArgsConstructor
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class UsernameValidator(private val userRepository: UserRepository) : ConstraintValidator<UsernameValid, String> {
    override fun isValid(p0: String, p1: ConstraintValidatorContext?) : Boolean {
        if(userRepository.findByUsername(p0)!=null) {
            throw DuplicatedUsernameException
        }
    }
}


//@Component
//class ImageValidator : ConstraintValidator<ImageValid, List<MultipartFile>> {
//    //https://hogwart-scholars.tistory.com/entry/Spring-Boot-ConstraintValidator%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-%EB%82%98%EB%A7%8C%EC%9D%98-validator-%EB%A7%8C%EB%93%A4%EA%B8%B0
//    //이걸 원래 여기서 throw 하는게 맞는지 헷갈리긴함. MethodValidException 던져줘서 그거 핸들링 했었는데 최근에는 달라진듯
//    override fun isValid(p0: List<MultipartFile>?, p1: ConstraintValidatorContext?): Boolean {
//        if (p0.isNullOrEmpty() || p0.any { !isImageFile(it) })
//            throw ParameterNotValidException("잘못된 이미지 파일입니다.", null)
//        return true
//    }
//
//    private fun isImageFile(file: MultipartFile): Boolean {
//        //만약 exe파일 업로드시 문제가 발생할 수 있음. ImageIO로 read해서 확인
//        return try {
//            ImageIO.read(ByteArrayInputStream(file.bytes.copyOf())) != null
//        } catch (e: Exception) {
//            false
//        }
//    }
//}