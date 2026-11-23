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
    AnimeItem("Academia eroilor", "2016", "Acțiune • Supereroi", "Izuku visează să devină un erou într-o lume în care aproape toți au puteri.", "NETFLIX RO"),
    AnimeItem("Hunter x Hunter", "2011", "Aventură • Acțiune", "Gon pornește într-o călătorie pentru a-și găsi tatăl și a deveni vânător.", "NETFLIX RO"),
    AnimeItem("Caietul morții", "2006", "Thriller • Mister", "Un elev descoperă un caiet cu puterea de a schimba destine.", "NETFLIX RO"),
    AnimeItem("The Seven Deadly Sins", "2014", "Fantasy • Acțiune", "O prințesă caută un grup legendar de cavaleri pentru a-și salva regatul.", "NETFLIX RO"),
    AnimeItem("Saga Vinland", "2019", "Acțiune • Istoric", "Un tânăr războinic caută sensul răzbunării într-o lume a vikingilor.", "NETFLIX RO"),
    AnimeItem("JoJo's Bizarre Adventure", "2012", "Acțiune • Supranatural", "Generații ale familiei Joestar se confruntă cu amenințări extraordinare.", "NETFLIX RO"),
    AnimeItem("Sword Art Online", "2012", "Aventură • SF", "Jucătorii rămân blocați într-un joc online periculos.", "NETFLIX RO"),
    AnimeItem("Profeția Ragnarok", "2021", "Acțiune • Fantasy", "Luptători legendari înfruntă zeii într-un turneu decisiv.", "NETFLIX RO"),
    AnimeItem("Cyberpunk: Edgerunners", "2022", "SF • Acțiune", "Un tânăr încearcă să supraviețuiască într-un oraș dominat de tehnologie.", "NETFLIX RO"),
    AnimeItem("Sakamoto Days", "2025", "Acțiune • Comedie", "Un fost asasin încearcă să ducă o viață liniștită.", "NETFLIX RO"),
    AnimeItem("Blue Box", "2024", "Romance • Sport", "Doi elevi se apropie prin sport și viața de liceu.", "NETFLIX RO"),
    AnimeItem("Kuroko", "2012", "Sport • Dramă", "O echipă de baschet încearcă să ajungă în vârful competiției.", "NETFLIX RO"),
    AnimeItem("Castlevania", "2017", "Fantasy • Acțiune", "Un vânător de vampiri luptă împotriva forțelor întunericului.", "NETFLIX RO"),
    AnimeItem("BAKI", "2018", "Acțiune • Arte marțiale", "Baki își testează limitele în confruntări extreme.", "NETFLIX RO"),
    AnimeItem("Hajime no Ippo: Lupta", "2000", "Sport • Dramă", "Un adolescent timid descoperă boxul și începe să se antreneze.", "NETFLIX RO"),
    AnimeItem("Căsnicia mea fericită", "2023", "Dramă • Fantasy", "O tânără descoperă iubirea și o lume plină de puteri misterioase.", "NETFLIX RO"),
    AnimeItem("Kakegurui", "2017", "Thriller • Dramă", "Într-o academie neobișnuită, statutul elevilor depinde de jocuri de noroc.", "NETFLIX RO"),
    AnimeItem("Baki Hanma", "2021", "Acțiune • Arte marțiale", "Baki se pregătește pentru cea mai grea confruntare a sa.", "NETFLIX RO"),
    AnimeItem("Parasyte: The Maxim", "2014", "SF • Horror", "Un adolescent împarte propriul corp cu o creatură extraterestră.", "NETFLIX RO"),
    AnimeItem("Ranma 1/2", "2024", "Comedie • Arte marțiale", "Un luptător este prins într-un blestem cu efecte neașteptate.", "NETFLIX RO"),
    AnimeItem("Viața dezastruoasă a lui Saiki K.", "2016", "Comedie • Supranatural", "Un adolescent cu puteri psihice vrea doar o viață normală.", "NETFLIX RO"),
    AnimeItem("Kengan Ashura", "2019", "Acțiune • Arte marțiale", "Luptători reprezintă companii în turnee brutale.", "NETFLIX RO"),
    AnimeItem("BEASTARS", "2019", "Dramă • Mister", "Un lup încearcă să-și înțeleagă instinctele într-o societate tensionată.", "NETFLIX RO"),
    AnimeItem("Monster", "2004", "Thriller • Mister", "Un medic pornește în căutarea unui fost pacient devenit periculos.", "NETFLIX RO"),
    AnimeItem("Komi nu știe să comunice", "2021", "Comedie • Romance", "O elevă timidă încearcă să-și facă o sută de prieteni.", "NETFLIX RO"),
    AnimeItem("Ouran High School Host Club", "2006", "Comedie • Romance", "O elevă ajunge într-un club școlar cu reguli neobișnuite.", "NETFLIX RO"),
    AnimeItem("The Summer Hikaru Died", "2025", "Mister • Supranatural", "Un adolescent observă că prietenul său s-a întors schimbat.", "NETFLIX RO"),
    AnimeItem("Delicious in Dungeon", "2024", "Fantasy • Aventură", "Un grup de aventurieri explorează o temniță și gătește creaturi fantastice.", "NETFLIX RO"),
    AnimeItem("Neon Genesis Evangelion", "1995", "SF • Dramă", "Adolescenți pilotează roboți uriași pentru a apăra omenirea.", "NETFLIX RO"),
    AnimeItem("Yu Yu Hakusho", "1992", "Acțiune • Supranatural", "Un adolescent devine detectiv al lumii spiritelor.", "NETFLIX RO"),
    AnimeItem("Regele șaman", "2021", "Aventură • Fantasy", "Șamanii se pregătesc pentru un turneu care decide Regele Șaman.", "NETFLIX RO"),
    AnimeItem("Violet Evergarden", "2018", "Dramă • Fantasy", "O fostă soldată învață să înțeleagă emoțiile oamenilor prin scrisori.", "NETFLIX RO"),
    AnimeItem("Cowboy Bebop", "1998", "SF • Aventură", "Vânători de recompense călătoresc prin spațiu.", "NETFLIX RO"),
    AnimeItem("Dorohedoro", "2020", "Fantasy • Acțiune", "Un bărbat cu cap de reptilă caută adevărul despre transformarea sa.", "NETFLIX RO"),
    AnimeItem("Aggretsuko", "2018", "Comedie • Viață", "O angajată își descarcă frustrările prin karaoke metal.", "NETFLIX RO"),
    AnimeItem("PLUTO", "2023", "SF • Mister", "Un detectiv robot investighează o serie de crime misterioase.", "ROMÂNĂ CONFIRMATĂ"),
    AnimeItem("Kotaro locuiește singur", "2022", "Comedie • Dramă", "Un băiețel neobișnuit schimbă viața vecinilor săi.", "NETFLIX RO"),
    AnimeItem("Blue Period", "2021", "Dramă • Artă", "Un elev descoperă pasiunea pentru pictură și intră într-o lume competitivă.", "NETFLIX RO"),
    AnimeItem("EDENS ZERO", "2021", "SF • Aventură", "Un tânăr pornește într-o călătorie prin univers.", "NETFLIX RO"),
    AnimeItem("Unchiul dintr-o altă lume", "2022", "Comedie • Fantasy", "Un bărbat se trezește după ani de comă cu amintiri dintr-o lume fantastică.", "NETFLIX RO"),
    AnimeItem("SPRIGGAN", "2022", "Acțiune • SF", "Agenți speciali protejează relicve ale unei civilizații antice.", "NETFLIX RO"),
    AnimeItem("Onmyoji", "2023", "Fantasy • Supranatural", "Un maestru al spiritelor rezolvă mistere în Japonia imperială.", "NETFLIX RO"),
    AnimeItem("My Daemon", "2023", "Aventură • SF", "Un băiat și o creatură specială pornesc într-o călătorie.", "NETFLIX RO"),
    AnimeItem("Superescrocii", "2021", "Acțiune • Crimă", "Un grup de infractori cu puteri speciale pregătește o lovitură.", "NETFLIX RO"),
    AnimeItem("Viața unui casnic", "2021", "Comedie • Viață", "Un fost membru yakuza se ocupă acum de treburile casei.", "NETFLIX RO"),
    AnimeItem("Leviathan", "2025", "SF • Aventură", "O poveste alternativă despre război și mașinării extraordinare.", "NETFLIX RO"),
    AnimeItem("GAMERA: Renașterea", "2023", "Acțiune • SF", "Copiii întâlnesc o creatură gigantică ce apără omenirea.", "NETFLIX RO"),
    AnimeItem("Noapte bună, lume!", "2023", "SF • Dramă", "O familie fragmentată se intersectează într-o lume virtuală.", "NETFLIX RO"),
    AnimeItem("Castlevania: Nocturne", "2023", "Fantasy • Acțiune", "Un vânător de vampiri se ridică împotriva unei noi amenințări.", "NETFLIX RO"),
    AnimeItem("DOTA: Sângele dragonului", "2021", "Fantasy • Acțiune", "Un cavaler dragon este prins într-un conflict între forțe magice.", "NETFLIX RO"),
    AnimeItem("Black Clover: Sabia Regelui Vrăjitor", "2023", "Fantasy • Film", "Asta și prietenii săi se confruntă cu foști regi vrăjitori.", "NETFLIX RO"),
    AnimeItem("Prințesa Mononoke", "1997", "Fantasy • Film", "Un tânăr ajunge în mijlocul conflictului dintre oameni și natură.", "NETFLIX RO"),
    AnimeItem("Băiatul și stârcul", "2023", "Fantasy • Film", "Un băiat descoperă o lume misterioasă după o pierdere.", "NETFLIX RO"),
    AnimeItem("Ponyo", "2008", "Fantasy • Film", "O creatură marină visează să devină om.", "NETFLIX RO"),
    AnimeItem("Vecinul meu Totoro", "1988", "Fantasy • Film", "Două surori descoperă spirite magice în apropierea casei.", "NETFLIX RO"),
    AnimeItem("Kiki: Livrări la domiciliu", "1989", "Aventură • Film", "O tânără vrăjitoare își începe viața independentă.", "NETFLIX RO"),
    AnimeItem("Povestea prințesei Kaguya", "2013", "Dramă • Film", "O fetiță misterioasă crește și își caută propriul drum.", "NETFLIX RO"),
    AnimeItem("Lumea secretă a lui Arrietty", "2010", "Aventură • Film", "O fetiță minusculă trăiește ascunsă în casa oamenilor.", "NETFLIX RO"),
    AnimeItem("Regatul pisicilor", "2002", "Fantasy • Film", "O adolescentă ajunge într-un regat fantastic al pisicilor.", "NETFLIX RO"),
    AnimeItem("Șoapte din inimă", "1995", "Dramă • Film", "O adolescentă își descoperă pasiunea pentru povești și scris.", "NETFLIX RO"),
    AnimeItem("Mary și floarea vrăjitoarei", "2017", "Fantasy • Film", "O fată descoperă o floare care îi oferă puteri magice.", "NETFLIX RO"),
    AnimeItem("Cosmica prințesă Kaguya!", "2026", "SF • Muzical • Film", "O fată misterioasă intră într-o lume virtuală plină de muzică.", "ROMÂNĂ CONFIRMATĂ"),
    AnimeItem("Attack on Titan", "2013", "Acțiune • Dramă • Fantasy", "Ultimii oameni trăiesc în spatele unor ziduri uriașe, amenințați de titani.", "CATALOG"),
    AnimeItem("Demon Slayer", "2019", "Acțiune • Fantasy", "Tanjiro pornește într-o călătorie pentru a-și salva sora.", "CATALOG"),
    AnimeItem("Jujutsu Kaisen", "2020", "Acțiune • Supranatural", "Un licean intră în lumea blestemelor și a vrăjitorilor.", "CATALOG"),
    AnimeItem("Fullmetal Alchemist: Brotherhood", "2009", "Aventură • Fantasy", "Doi frați caută o cale de a-și repara greșeala făcută prin alchimie.", "CATALOG"),
    AnimeItem("One Piece", "1999", "Aventură • Acțiune", "Luffy și echipajul său pornesc în căutarea celei mai mari comori.", "CATALOG"),
    AnimeItem("Naruto", "2002", "Acțiune • Aventură", "Un tânăr ninja visează să devină liderul satului său.", "CATALOG"),
    AnimeItem("Spy x Family", "2022", "Comedie • Acțiune", "Un spion, o asasină și o fetiță telepată formează o familie neobișnuită.", "CATALOG"),
    AnimeItem("Your Name", "2016", "Romance • Fantasy • Film", "Doi adolescenți descoperă o legătură misterioasă între viețile lor.", "CATALOG"),
    AnimeItem("Spirited Away", "2001", "Aventură • Fantasy • Film", "O fată ajunge într-o lume magică și încearcă să-și salveze părinții.", "CATALOG")
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
            Text("Anime din catalogul Netflix România • verifică subtitrarea română pe pagina fiecărui titlu", color = muted, fontSize = 14.sp)
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
                        val url = "https://www.netflix.com/ro/search?q=" + Uri.encode(item.title)
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
            Button(onClick = onSearch) { Text("Deschide Netflix") }
        }
    }
}
