package io.moatwel.github.domain.entity.event

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class DeletePayload(
  @Json(name = "ref_type")
  val refType: String,

  val ref: String
) : Serializable