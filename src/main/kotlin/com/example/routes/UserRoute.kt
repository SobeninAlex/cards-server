package com.example.routes

import com.example.authentification.hash
import com.example.data.model.RoleModel
import com.example.data.model.UserModel
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.RegisterRequest
import com.example.data.model.response.BaseResponse
import com.example.domain.usecase.UserUseCase
import com.example.utils.ErrorMessage
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(userUseCase: UserUseCase) {
    val hashFunction: (String) -> String = { str: String ->
        hash(password = str)
    }

    //register new user
    post("api/v1/signup") {
        val registerRequest = call.receiveNullable<RegisterRequest>() ?: run {
            call.respond(
                HttpStatusCode.BadRequest,
                BaseResponse(
                    isSuccess = false,
                    message = ErrorMessage.SOMETHING_WRONG
                )
            )
            return@post
        }

        try {
            val user = UserModel(
                id = 0,
                email = registerRequest.email.trim().lowercase(),
                login = registerRequest.login.trim().lowercase(),
                password = hashFunction(registerRequest.password.trim()),
                firstName = registerRequest.firstName.trim(),
                lastName = registerRequest.lastName.trim(),
                isActive = registerRequest.isActive,
                role = RoleModel.getRoleModelFromString(registerRequest.role.trim().lowercase()),
            )

            userUseCase.createUser(userModel = user)
            call.respond(
                HttpStatusCode.Created,
                BaseResponse(
                    isSuccess = true,
                    message = userUseCase.generateToken(user)
                )
            )
        } catch (ex: Exception) {
            call.respond(
                HttpStatusCode.Conflict,
                BaseResponse(
                    isSuccess = false,
                    message = ex.message ?: ErrorMessage.SOMETHING_WRONG
                )
            )
        }
    }

    //login user
    post("api/v1/login") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: run {
            call.respond(
                HttpStatusCode.BadRequest,
                BaseResponse(
                    isSuccess = false,
                    message = ErrorMessage.SOMETHING_WRONG
                )
            )
            return@post
        }

        try {
            val user = userUseCase.getUserByEmail(loginRequest.email.trim().lowercase())
            if (user == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    BaseResponse(
                        isSuccess = false,
                        message = ErrorMessage.WRONG_EMAIL
                    )
                )
            } else {
                if (user.password == hashFunction(loginRequest.password)) {
                    call.respond(
                        HttpStatusCode.OK,
                        BaseResponse(
                            isSuccess = true,
                            message = userUseCase.generateToken(user)
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        BaseResponse(
                            isSuccess = false,
                            message = ErrorMessage.WRONG_PASSWORD
                        )
                    )
                }
            }
        } catch (ex: Exception) {
            call.respond(
                HttpStatusCode.Conflict,
                BaseResponse(
                    isSuccess = false,
                    message = ex.message ?: ErrorMessage.SOMETHING_WRONG
                )
            )
        }
    }

    authenticate("jwt") {
        get("api/v1/get_user_info") {
            try {
                val user = call.principal<UserModel>()
                if (user != null) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = user
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = BaseResponse(
                            isSuccess = false,
                            message = ErrorMessage.USER_NOT_FOUND
                        )
                    )
                }
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




















