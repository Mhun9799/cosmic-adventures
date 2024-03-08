package org.team.b4.cosmicadventures.domain.user.dto.response

import org.team.b4.cosmicadventures.domain.user.model.User

data class UserResponse(

    var id: Long,

    var name: String,

    var email: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    var profilePicUrl: MutableList<String>,
){

    companion object{
        fun from(user: User) = UserResponse(
            id = user.id!!,
            name = user.name,
            email = user.email,
            introduction = user.introduction,
            tlno = user.tlno,
            role = user.role.name,
            profilePicUrl = user.profilePicUrl,
        )
    }
}
