package com.kursatmemis.doviz_kurlari.results

import com.kursatmemis.doviz_kurlari.models.Currency
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class XMLResult {

    fun currency(): Pair<MutableList<Currency>, Pair<String, String>> {
        val currencies = mutableListOf<Currency>()

        val url = "https://www.tcmb.gov.tr/kurlar/today.xml"
        val doc: Document = Jsoup.connect(url).timeout(30000).ignoreContentType(true).get()
        val elements = doc.getElementsByTag("Currency")
        val parent = elements.parents()
        val Tarih = parent.attr("Tarih")
        val Bulten_No = parent.attr("Bulten_No")

        for (element in elements) {
            val Isim = element.getElementsByTag("Isim").text()
            val ForexBuying = element.getElementsByTag("ForexBuying").text()
            val ForexSelling = element.getElementsByTag("ForexSelling").text()
            val BanknoteBuying = element.getElementsByTag("BanknoteBuying").text()
            val BanknoteSelling = element.getElementsByTag("BanknoteSelling").text()
            val currency =
                Currency(Isim, ForexBuying, ForexSelling, BanknoteBuying, BanknoteSelling)
            currencies.add(currency)
        }
        return Pair(currencies, Pair(Tarih, Bulten_No))
    }
}