package com.example.routes

import com.example.data.model.CardModel
import com.example.data.model.UserModel
import com.example.data.model.request.AddCardRequest
import com.example.data.model.response.BaseResponse
import com.example.domain.usecase.CardUseCase
import com.example.utils.ErrorMessage
import com.example.utils.SuccessMessage
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cardRoute(cardUseCase: CardUseCase) {
    authenticate("jwt") {
        get("api/v1/get_all_cards") {
            try {
                val cards = cardUseCase.getAllCards()
                call.respond(HttpStatusCode.OK, cards)
            } catch (ex: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = BaseResponse(
                        isSuccess = false,
                        message = ex.message ?: ErrorMessage.SOMETHING_WRONG
                    )
                )
            }
        }

        post("api/v1/create_card") {
            val addCardRequest = call.receiveNullable<AddCardRequest>() ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(
                        isSuccess = false,
                        message = ErrorMessage.SOMETHING_WRONG
                    )
                )
                return@post
            }

            try {
                val card = CardModel(
                    id = 0,
                    ownerId = call.principal<UserModel>()!!.id,
                    cardTitle = addCardRequest.cardTitle,
                    cardDescription = addCardRequest.cardDescription,
                    createdAt = addCardRequest.createdAt,
                    isVerified = addCardRequest.isVerified,
                )

                cardUseCase.addCard(card)
                call.respond(
                    status = HttpStatusCode.Created,
                    message = BaseResponse(
                        isSuccess = true,
                        message = SuccessMessage.CARD_ADDED_SUCCESSFULLY
                    )
                )
            } catch (ex: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = BaseResponse(
                        isSuccess = false,
                        message = ex.message ?: ErrorMessage.SOMETHING_WRONG
                    )
                )
            }
        }

        post("api/v1/update_card") {
            val request = call.receiveNullable<AddCardRequest>() ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(
                        isSuccess = false,
                        message = ErrorMessage.SOMETHING_WRONG
                    )
                )
                return@post
            }

            try {
                val ownerId = call.principal<UserModel>()!!.id
                val card = CardModel(
                    id = request.id ?: 0,
                    ownerId = ownerId,
                    cardTitle = request.cardTitle,
                    cardDescription = request.cardDescription,
                    createdAt = request.createdAt,
                    isVerified = request.isVerified,
                )

                cardUseCase.updateCard(card = card, owner = ownerId)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(
                        isSuccess = true,
                        message = SuccessMessage.CARD_UPDATED_SUCCESSFULLY
                    )
                )
            } catch (ex: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = BaseResponse(
                        isSuccess = false,
                        message = ex.message ?: ErrorMessage.SOMETHING_WRONG
                    )
                )
            }
        }

        delete("api/v1/delete_card") {
            val request = call.request.queryParameters["id"]?.toInt() ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(
                        isSuccess = false,
                        message = ErrorMessage.SOMETHING_WRONG
                    )
                )
                return@delete
            }

            try {
                val ownerId = call.principal<UserModel>()!!.id
                cardUseCase.deleteCard(cardId = request, owner = ownerId)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(
                        isSuccess = true,
                        message = SuccessMessage.CARD_DELETED_SUCCESSFULLY
                    )
                )
            } catch (ex: Exception) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = BaseResponse(
                        isSuccess = false,
                        message = ex.message ?: ErrorMessage.SOMETHING_WRONG
                    )
                )
            }
        }
    }
}