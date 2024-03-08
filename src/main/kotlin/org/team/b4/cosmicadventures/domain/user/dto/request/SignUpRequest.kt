package org.team.b4.cosmicadventures.domain.user.dto.request


import jakarta.validation.constraints.Email
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile
import org.team.b4.cosmicadventures.domain.user.model.Role
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
        return profilePic?.get(0)?.originalFilename == ""
    }

    fun to() = User (
        role = Role.USER,
        name = name,
        email = email,
        password = password,
        introduction = introduction,
        tlno = tlno,
    )
}