package com.example.demo.docs.util

import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.HWPChar
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.HWPCharNormal
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.HWPCharType

class HWPUtil {

    companion object {
        fun setParagraphString(para : Paragraph, text : String) {
            val newChar = convertHWPChar(para, text)
            //해당 paragraph내 문자 제거
            para.text.charList.let {
                it.clear()
                it.addAll(newChar)
            }

            //포맷 변경
            para.header.characterCount = newChar.size.toLong()
            para.deleteLineSeg()

            //shape 변경
            removeCharShapeExceptFirst(para)
        }

        private fun removeCharShapeExceptFirst(para: Paragraph) {
            val size: Int = para.charShape.positonShapeIdPairList.size
            if (size > 1) {
                for (index in 0 until size - 1) {
                    para.charShape.positonShapeIdPairList.removeAt(1)
                }
                para.header.charShapeCount = 1
            }
        }

        private fun convertHWPChar(para: Paragraph, content : String): MutableList<HWPChar> {
            //HwpChar로 변환
            val convertCharList = toHWPCharArray(content)
            //컨트롤 데이터 넣기
            convertCharList.addAll(para.text.charList.filter { it.type != HWPCharType.Normal })
            //반환.
            return convertCharList
        }

        private fun toHWPCharArray(text: String): MutableList<HWPChar> {
            val list = ArrayList<HWPChar>()
            for (index in text.indices) {
                val code = text.codePointAt(index) //to char code
                list.add(HWPCharNormal(code))
            }

            return list
        }
    }

}