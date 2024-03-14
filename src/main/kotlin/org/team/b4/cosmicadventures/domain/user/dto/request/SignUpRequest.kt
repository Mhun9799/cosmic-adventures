package org.team.b4.cosmicadventures.domain.user.dto.request


import jakarta.validation.constraints.Email
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile
import org.team.b4.cosmicadventures.domain.user.model.Role
import org.team.b4.cosmicadventures.domain.user.model.Status
import org.team.b4.cosmicadventures.domain.user.model.User
import org.team.b4.cosmicadventures.global.validation.ValidPassword
import org.team.b4.cosmicadventures.global.validation.ValidTlno

@Validated
data class SignUpRequest(

    var name: String,

    @field:Email
    var email: String,

    @field: ValidPassword
    var password: String,

    @field: ValidPassword
    var confirmpassword:String,

    var introduction: String,

    @field: ValidTlno
    var tlno: String,

    var role: String,

    var profilePic:MutableList<MultipartFile>?
){
    fun isPicsEmpty(): Boolean {
        return profilePic?.getOrNull(0)?.originalFilename.isNullOrEmpty()
    }

    fun to(): User {
        val user = User(
            role = Role.USER,
            name = name,
            email = email,
            password = password,
            introduction = introduction,
            tlno = tlno,
            status = Status.NORMAL
        )

        //여기서는 회원가입 요청이 들어올 때, 프로필 이미지가 없을 경우에 대비하여 기본 이미지 URL을 설정
        user.profilePicUrl = listOf("https://imgur.com/S8jQ6wN").toMutableList()
        return user
    }
}
