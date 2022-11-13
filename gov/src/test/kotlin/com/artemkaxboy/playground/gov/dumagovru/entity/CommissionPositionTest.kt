package com.artemkaxboy.playground.gov.dumagovru.entity

internal class CommissionPositionTest

fun makeCommissionPosition(
    person: Person = makePerson(),
    personId: Long = person.id ?: 1,
    commission: Commission = makeCommission(),
    commissionId: Long = commission.id ?: 1,
    positionText: String = "positionText",
    positionType: String? = "positionType",
): CommissionPosition = CommissionPosition(
    person = person,
    personId = personId,
    commission = commission,
    commissionId = commissionId,
    positionText = positionText,
    positionType = positionType,
)
