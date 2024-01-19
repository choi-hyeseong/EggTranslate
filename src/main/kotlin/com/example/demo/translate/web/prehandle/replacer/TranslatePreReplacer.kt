package com.example.demo.translate.web.prehandle.replacer

interface TranslatePreReplacer {

    /*
    * OcrPostReplacer랑 다른게 뭐냐라고 생각하면 파라미터 한개 차이일뿐.
    * 따라서 OcrPostReplacer를 공용 인터페이스로 바꾸고 이를 구현해도 됨.
    * 그러면 이 인터페이스는 생성자에다 lang만 넣어주면 위 replace메소드를 그대로 쓸 수 있음.
    * 근데 각 언어별로 Replacer가 생성되는게 맞는건가? 같은 '언어'를 받아서 동일한 로직에 따라 바꿔주는건데
    * 그래서 PostReplacer와 PreReplacer를 분리하였음. 더 좋은 방식이 생각이 안남.
     */
    fun replace(lang : String, input : String) : String

}