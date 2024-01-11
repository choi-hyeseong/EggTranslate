package com.example.demo.image.validation

import com.example.demo.common.exception.ParameterNotValidException
import com.example.demo.common.valid.lang.LangValidator
import com.example.demo.image.dto.ImageDTO
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO


@Component
class ImageValidator(
    private val langValidator: LangValidator
) : ConstraintValidator<ImageValid, ImageDTO> {
    //https://hogwart-scholars.tistory.com/entry/Spring-Boot-ConstraintValidator%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-%EB%82%98%EB%A7%8C%EC%9D%98-validator-%EB%A7%8C%EB%93%A4%EA%B8%B0
    //이걸 원래 여기서 throw 하는게 맞는지 헷갈리긴함. MethodValidException 던져줘서 그거 핸들링 했었는데 최근에는 달라진듯
    override fun isValid(p0: ImageDTO?, p1: ConstraintValidatorContext?): Boolean {
        if (p0 == null)
            throw ParameterNotValidException("이미지 데이터는 빈 값이 올 수 없습니다.", null)

        if (p0.file.isEmpty() || p0.file.any { !isImageFile(it) })
            throw ParameterNotValidException("잘못된 이미지 파일입니다.", null)

        return langValidator.isValid(p0.lang, p1)
    }

    private fun isImageFile(file: MultipartFile): Boolean {
        //만약 exe파일 업로드시 문제가 발생할 수 있음. ImageIO로 read해서 확인
        return try {
            ImageIO.read(ByteArrayInputStream(file.bytes.copyOf())) != null
        } catch (e: Exception) {
            false
        }
    }
}

/**
 * 만약 Composite 패턴으로 한다면 LangValid와 ImageValid를 제거하고 Validator 인터페이스를 구현한다
 *  supports 메소드에서 class를 받아와서 ImageValidator는 ImageDTO만, LangValidator는 STring + content equals
 *  이후 CustomVAlidator를 만들고 해당 validator는 List<Validtor>를 스프링으로부터 주입받아
 *  입력받은 필드 (Any)가 support한지 검증하고 검증기를 돌린다.
 */