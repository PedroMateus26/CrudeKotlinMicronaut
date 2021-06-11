package com.pedromateus.zup.entity

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class BatalhaEntity(
    @Column(nullable = false)
    var classe:String?,

    @Column(nullable = false)
    var raca:String?,

    @Column(nullable = false)
    var profissao:String?
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null
    @CreationTimestamp
    var criadoEm=LocalDateTime.now()
}