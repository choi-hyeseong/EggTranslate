package com.example.demo.docs.component.adobe

import com.adobe.pdfservices.operation.PDFServices
import com.adobe.pdfservices.operation.PDFServicesMediaType
import com.adobe.pdfservices.operation.pdfjobs.jobs.ExportPDFJob
import com.adobe.pdfservices.operation.pdfjobs.params.exportpdf.ExportPDFParams
import com.adobe.pdfservices.operation.pdfjobs.params.exportpdf.ExportPDFTargetFormat
import com.adobe.pdfservices.operation.pdfjobs.result.ExportPDFResult
import com.example.demo.docs.exception.DocumentException
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.OutputStream

@Component
class PDFConverter(private val pdfServices: PDFServices) {

    fun convertPdfToDocx(stream : ByteArrayInputStream) : ByteArrayInputStream {
        val pollUrl = submitPDF(stream)
        val response = readDocx(pollUrl)
        return ByteArrayInputStream(response.readAllBytes()) //ByteArrayStream으로 변경.
    }

    //pdf를 adobe에 업로드 하고, polling 주소를 얻는 메소드
    private fun submitPDF(inputStream: InputStream) : String {
        val asset = pdfServices.upload(inputStream, PDFServicesMediaType.PDF.mediaType)
        val exportParam = ExportPDFParams.exportPDFParamsBuilder(ExportPDFTargetFormat.DOCX).build() //docx로 추출한다.
        val job = ExportPDFJob(asset, exportParam)
        return pdfServices.submit(job)
    }

    private fun readDocx(pollingUrl : String) : InputStream {
        val response = pdfServices.getJobResult(pollingUrl, ExportPDFResult::class.java)
        //respose.status로 결과 체크 가능.
        if ("failed" == response.status)
            throw DocumentException("PDF 파일 변환에 실패하였습니다. 다시 시도해주세요.")

        val resultAsset = response.result.asset
        return pdfServices.getContent(resultAsset).inputStream //실제 다운로드 스트림
    }
}