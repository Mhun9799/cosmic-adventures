package org.team.b4.cosmicadventures.domain.eonet.model


import jakarta.persistence.*

@Entity
@Table(name = "nasa_event")
class NasaEvent(
    @Column(nullable = false)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String? = null,

    @Column(nullable = false)
    val description: String? = null,

    @Column(nullable = false)
    val link: String? = null,

    @Column(name = "closed")
    @Enumerated(EnumType.STRING)
    val closed: EventStatus? = null
) {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val eventId: Long? = null

//    private fun extractNasaEventFromApiResponse(eventResponse: String?):  {
//        // Jackson ObjectMapper를 사용하여 JSON 형식의 API 응답을 파싱
//        val objectMapper = ObjectMapper()
//        val node = objectMapper.readTree(eventResponse)
//        // JSON 노드에서 필요한 정보를 추출
//        val title = node["title"].asText()
//        val explanation = node["explanation"]?.asText()
//        val date = LocalDate.parse(node["date"].asText())
//        val hdurl = URL(node["hdurl"].asText())
//        val mediaType = node["media_type"].asText()
//        val serviceVersion = node["service_version"].asText()
//        val url = URL(node["url"].asText())
//        // 추출한 정보로 NasaImage 객체 생성 및 반환
//        return NasaImage(title, explanation, date, hdurl, mediaType, serviceVersion, url)
//    }
}