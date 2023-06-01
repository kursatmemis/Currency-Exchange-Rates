package com.kursatmemis.doviz_kurlari

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.kursatmemis.doviz_kurlari.models.Currency
import com.kursatmemis.doviz_kurlari.results.XMLResult

class MainActivity : AppCompatActivity() {
    private lateinit var showCurrenciesButton: Button
    private lateinit var forexBuyingValueTextView: TextView
    private lateinit var forexSellingValueTextView: TextView
    private lateinit var bankNoteBuyingValueTextView: TextView
    private lateinit var bankNoteSellingValueTextView: TextView
    private lateinit var dateInfoTextView: TextView
    private lateinit var currencyNameTextView: TextView
    private lateinit var result: Pair<MutableList<Currency>, Pair<String, String>>
    private lateinit var currencies: MutableList<Currency>
    private lateinit var tarih: String
    private lateinit var bultenNo: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViews()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val xmlResult = XMLResult()
        try {
            result = xmlResult.currency()
            currencies = result.first
            tarih = result.second.first
            bultenNo = result.second.second
        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.toast_message),
                Toast.LENGTH_SHORT
            ).show()
            Thread.sleep(2500)
            finish()
            return
        }
        dateInfoTextView.text = "$tarih ${getString(R.string.date_info_text)}\nBülten No:$bultenNo"
        val popupMenu = setMenu()
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId in 0 until currencies.size) {
                val currency = currencies[it.itemId]
                setTexts(currency)
                true
            } else {
                false
            }
        }
        showCurrenciesButton.setOnClickListener {
            popupMenu.show()
        }

    }

    private fun setTexts(currency: Currency) {
        val currencyName = currency.Isim
        val forexBuyingText = currency.ForexBuying
        val forexSellingText = currency.ForexSelling
        val bankNoteBuyingText = currency.BanknoteBuying
        val bankNoteSellingText = currency.BanknoteSelling
        forexBuyingValueTextView.text = forexBuyingText
        forexSellingValueTextView.text = forexSellingText
        bankNoteBuyingValueTextView.text = bankNoteBuyingText
        bankNoteSellingValueTextView.text = bankNoteSellingText
        currencyNameTextView.text = currencyName
    }

    private fun setMenu(): PopupMenu {
        val popupMenu = PopupMenu(
            this@MainActivity,
            showCurrenciesButton,
            Gravity.TOP,
            0,
            R.style.MyPopupMenuStyle
        )
        menuInflater.inflate(R.menu.currencies_menu, popupMenu.menu)

        for (item in 0 until currencies.size) {
            popupMenu.menu.add(0, item, item, currencies[item].Isim)
        }
        return popupMenu
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        showCurrenciesButton = findViewById(R.id.showCurrenciesButton)
        forexBuyingValueTextView = findViewById(R.id.forexBuyingValueTextView)
        forexSellingValueTextView = findViewById(R.id.forexSellingValueTextView)
        bankNoteBuyingValueTextView = findViewById(R.id.bankNoteBuyingValueTextView)
        bankNoteSellingValueTextView = findViewById(R.id.bankNoteSellingValueTextView)
        dateInfoTextView = findViewById(R.id.dateInfoTextView)
        currencyNameTextView = findViewById(R.id.currencyNameTextView)
    }
}