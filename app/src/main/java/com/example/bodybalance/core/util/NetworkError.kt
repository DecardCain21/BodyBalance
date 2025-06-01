package com.example.bodybalance.core.util

public sealed class NetworkError(override val message: String) : Throwable(message = message) {

    public class ServerError(message: String) :
        NetworkError("Ошибка при подключении к серверу: сообщение: $message")

    public class NoData: NetworkError("Пустое тело ответа")

    public class BadRequest : NetworkError("Ресурс не найден")

    public class NoInternet : NetworkError("No internet connection")
}