package com.example.demo.dto.push

data class FirebaseResponseDTO(var successCount: Int, var failCount: Int, var message: String) {

    companion object {
        fun empty() = FirebaseResponseDTO(0, 0, "")
    }

    //여러 Response를 묶을 수 있는 함수
    private fun merge(success: Int, fail: Int, content: String): FirebaseResponseDTO {
        return this.apply {
            successCount += success
            failCount += fail
            message = if (this.isEmpty()) message else message.plus("\n$content")
        }
    }

    fun merge(responseDTO: FirebaseResponseDTO): FirebaseResponseDTO {
        return merge(responseDTO.successCount, responseDTO.failCount, responseDTO.message)
    }

    fun isEmpty() = successCount == 0 && failCount == 0
}