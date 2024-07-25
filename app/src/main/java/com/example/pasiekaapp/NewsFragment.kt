package com.example.pasiekaapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        fetchNewsData()

        return view
    }

    private fun fetchNewsData() {
        // Przykładowe dane, w rzeczywistej aplikacji dane będą pobierane z API
        val newsList = listOf(
            News("Zanieczyszczenie powietrza a populacja pszczół", "https://pasieka24.pl/index.php/pl-pl/aktualnosci/wiadomosci-ze-swiata/2717-pszczoly-a-zanieczyszczenie-powietrza"),
            News("Pszczoły mogą odczuwać ból", "https://naukawpolsce.pl/aktualnosci/news%2C93196%2Cbadania-pszczoly-i-inne-owady-moga-odczuwac-pewna-forme-bolu.html")
        )

        newsAdapter = NewsAdapter(newsList) { news ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = newsAdapter
    }
}
