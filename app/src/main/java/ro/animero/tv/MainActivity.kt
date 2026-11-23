package ro.animero.tv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class AnimeItem(
    val title: String, val year: String, val genres: String,
    val description: String, val badge: String
)

private val catalog = listOf(
    AnimeItem("Attack on Titan", "2013", "Acțiune • Dramă • Fantasy", "Ultimii oameni trăiesc în spatele unor ziduri uriașe, amenințați de titani.", "POPULAR"),
    AnimeItem("Demon Slayer", "2019", "Acțiune • Fantasy", "Tanjiro pornește într-o călătorie pentru a-și salva sora.", "POPULAR"),
    AnimeItem("Jujutsu Kaisen", "2020", "Acțiune • Supranatural", "Un licean intră în lumea blestemelor și a vrăjitorilor.", "TRENDING"),
    AnimeItem("Death Note", "2006", "Thriller • Mister", "Un caiet misterios schimbă viața unui elev foarte inteligent.", "CLASIC"),
    AnimeItem("Fullmetal Alchemist: Brotherhood", "2009", "Aventură • Fantasy", "Doi frați caută o cale de a-și repara greșeala făcută prin alchimie.", "CLASIC"),
    AnimeItem("One Piece", "1999", "Aventură • Acțiune", "Luffy și echipajul său pornesc în căutarea celei mai mari comori.", "POPULAR"),
    AnimeItem("Naruto", "2002", "Acțiune • Aventură", "Un tânăr ninja visează să devină liderul satului său.", "CLASIC"),
    AnimeItem("Spy x Family", "2022", "Comedie • Acțiune", "Un spion, o asasină și o fetiță telepată formează o familie neobișnuită.", "FAMILY"),
    AnimeItem("Your Name", "2016", "Romance • Fantasy", "Doi adolescenți descoperă o legătură misterioasă între viețile lor.", "FILM"),
    AnimeItem("Spirited Away", "2001", "Aventură • Fantasy", "O fată ajunge într-o lume magică și încearcă să-și salveze părinții.", "FILM")
)

private val bg = Color(0xFF080B14)
private val panel = Color(0xFF151B2B)
private val accent = Color(0xFFFFB000)
private val muted = Color(0xFF9BA6BD)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AnimeRoApp() }
    }
}

@Composable
private fun AnimeRoApp() {
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf("Toate") }
    var favorites by remember { mutableStateOf(setOf<String>()) }
    var showFavorites by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val categories = listOf("Toate", "Acțiune", "Aventură", "Fantasy", "Filme")

    val filtered = catalog.filter { item ->
        val matchesQuery = item.title.contains(query, ignoreCase = true) ||
            item.genres.contains(query, ignoreCase = true)
        val matchesCategory = selected == "Toate" ||
            (selected == "Filme" && item.badge == "FILM") ||
            item.genres.contains(selected, ignoreCase = true)
        matchesQuery && matchesCategory && (!showFavorites || favorites.contains(item.title))
    }

    MaterialTheme(colorScheme = darkColorScheme(
        background = bg, surface = panel, primary = accent,
        onBackground = Color.White, onSurface = Color.White
    )) {
        Column(Modifier.fillMaxSize().background(bg).padding(horizontal = 34.dp, vertical = 22.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("ANIME", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                Text(" RO", color = accent, fontSize = 30.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.width(18.dp))
                Text("UNIVERSUL ANIME, ÎNTR-UN SINGUR LOC", color = muted, fontSize = 11.sp)
                Spacer(Modifier.weight(1f))
                Text("v1.0.0", color = muted, fontSize = 12.sp)
            }
            Spacer(Modifier.height(18.dp))
            Text("Descoperă următorul tău anime.", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text("Catalog în română • Informații • Căutare platforme oficiale", color = muted, fontSize = 14.sp)
            Spacer(Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Surface(color = panel, shape = RoundedCornerShape(12.dp), modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = query, onValueChange = { query = it }, singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 13.dp),
                        decorationBox = { inner ->
                            if (query.isEmpty()) Text("Caută anime...", color = muted, fontSize = 16.sp)
                            inner()
                        }
                    )
                }
                Button(onClick = { showFavorites = !showFavorites }) {
                    Text(if (showFavorites) "Toate" else "♥ Favorite")
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.forEach { category ->
                    FilterChip(selected = selected == category, onClick = { selected = category }, label = { Text(category) })
                }
            }
            Spacer(Modifier.height(14.dp))
            Text(if (showFavorites) "FAVORITELE TALE" else "CATALOG ANIME", color = accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(bottom = 24.dp)) {
                items(filtered, key = { it.title }) { item ->
                    AnimeCard(item, favorites.contains(item.title), {
                        favorites = if (favorites.contains(item.title)) favorites - item.title else favorites + item.title
                    }) {
                        val url = "https://www.justwatch.com/ro/cautare?q=" + Uri.encode(item.title)
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }
                }
                if (filtered.isEmpty()) item {
                    Text("Nu am găsit rezultate. Încearcă altă căutare.", color = muted, modifier = Modifier.padding(18.dp))
                }
            }
        }
    }
}

@Composable
private fun AnimeCard(item: AnimeItem, favorite: Boolean, onFavorite: () -> Unit, onSearch: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Surface(
        color = if (focused) Color(0xFF26334C) else panel,
        shape = RoundedCornerShape(14.dp),
        border = if (focused) BorderStroke(2.dp, accent) else null,
        modifier = Modifier.fillMaxWidth().onFocusChanged { focused = it.hasFocus }.focusable()
    ) {
        Row(Modifier.padding(horizontal = 18.dp, vertical = 13.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(10.dp))
                    Text(item.badge, color = accent, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(4.dp))
                Text("${item.year}  •  ${item.genres}", color = muted, fontSize = 12.sp)
                Spacer(Modifier.height(5.dp))
                Text(item.description, color = Color(0xFFD8DEEA), fontSize = 13.sp, maxLines = 2)
            }
            Spacer(Modifier.width(12.dp))
            TextButton(onClick = onFavorite) { Text(if (favorite) "♥" else "♡", color = accent, fontSize = 22.sp) }
            Button(onClick = onSearch) { Text("Unde îl văd?") }
        }
    }
}
