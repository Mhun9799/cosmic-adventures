package org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.converter

import org.springframework.core.convert.converter.Converter
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider



class OAuth2ProviderConverter : Converter<String, OAuth2Provider> {

    override fun convert(source: String): OAuth2Provider {
        return runCatching {
            OAuth2Provider.valueOf(source.uppercase())
        }.getOrElse {
            throw IllegalArgumentException()
        }
    }
}