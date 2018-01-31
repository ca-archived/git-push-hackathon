package io.moatwel.github.domain.entity.event

import com.squareup.moshi.Json
import java.io.Serializable

data class DeletePayload(
  @Json(name = "ref_type")
  val refType: String,

  val ref: String
) : Serializable