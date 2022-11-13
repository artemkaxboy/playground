package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationDataDto(

    val persons: Set<PersonDto>,
    val fractions: Set<FractionDto>,
    @SerialName("int_groups")
    val intGroups: Set<IntGroupDto>,
    @SerialName("int_commissions")
    val intCommissions: Set<IntCommissionDto>,
    val countries: Set<CountryDto>,
    val commissions: Set<CommissionDto>,
    val regions: Set<RegionDto>,
    val convocations: Set<ConvocationDto>,
    @SerialName("last_convocation")
    val lastConvocation: Long,
    val version: String,
)
