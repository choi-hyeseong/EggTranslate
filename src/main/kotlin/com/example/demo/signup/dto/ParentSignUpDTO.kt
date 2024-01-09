package com.example.demo.signup.dto
<<<<<<< HEAD

class ParentSignUpDTO {
}
=======
import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.parent.dto.ParentDTO

class ParentSignUpDTO(
    var children : MutableList<ChildRequestDto>,
    val user : UserSignUpDTO

) {
    fun toParentDTO() : ParentDTO = ParentDTO(-1, children, user.toUserDTO())

}
>>>>>>> b935c0f74224b0e6e4979396c72e57983ee28f69
