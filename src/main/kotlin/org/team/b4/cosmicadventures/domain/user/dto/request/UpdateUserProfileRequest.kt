package org.team.b4.cosmicadventures.domain.user.dto.request


import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile
import org.team.b4.cosmicadventures.global.validation.ValidTlno

@Validated
data class UpdateUserProfileRequest(

    var name: String,

    var introduction: String,

    @field: ValidTlno
    var tlno: String,

    var profilePicUrl: MutableList<MultipartFile> = mutableListOf()
)
