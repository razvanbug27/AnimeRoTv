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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

private data class AnimeItem(
    val title: String, val year: String, val genres: String,
    val description: String, val badge: String
)

private val catalog = listOf(
    AnimeItem("Academia eroilor", "2016", "Acțiune • Supereroi", "Izuku visează să devină un erou într-o lume în care aproape toți au puteri.", "VERIFICĂ ÎN RO"),
    AnimeItem("Hunter x Hunter", "2011", "Aventură • Acțiune", "Gon pornește într-o călătorie pentru a-și găsi tatăl și a deveni vânător.", "VERIFICĂ ÎN RO"),
    AnimeItem("Caietul morții", "2006", "Thriller • Mister", "Un elev descoperă un caiet cu puterea de a schimba destine.", "VERIFICĂ ÎN RO"),
    AnimeItem("The Seven Deadly Sins", "2014", "Fantasy • Acțiune", "O prințesă caută un grup legendar de cavaleri pentru a-și salva regatul.", "VERIFICĂ ÎN RO"),
    AnimeItem("Saga Vinland", "2019", "Acțiune • Istoric", "Un tânăr războinic caută sensul răzbunării într-o lume a vikingilor.", "VERIFICĂ ÎN RO"),
    AnimeItem("JoJo's Bizarre Adventure", "2012", "Acțiune • Supranatural", "Generații ale familiei Joestar se confruntă cu amenințări extraordinare.", "VERIFICĂ ÎN RO"),
    AnimeItem("Sword Art Online", "2012", "Aventură • SF", "Jucătorii rămân blocați într-un joc online periculos.", "VERIFICĂ ÎN RO"),
    AnimeItem("Profeția Ragnarok", "2021", "Acțiune • Fantasy", "Luptători legendari înfruntă zeii într-un turneu decisiv.", "VERIFICĂ ÎN RO"),
    AnimeItem("Cyberpunk: Edgerunners", "2022", "SF • Acțiune", "Un tânăr încearcă să supraviețuiască într-un oraș dominat de tehnologie.", "VERIFICĂ ÎN RO"),
    AnimeItem("Sakamoto Days", "2025", "Acțiune • Comedie", "Un fost asasin încearcă să ducă o viață liniștită.", "VERIFICĂ ÎN RO"),
    AnimeItem("Blue Box", "2024", "Romance • Sport", "Doi elevi se apropie prin sport și viața de liceu.", "VERIFICĂ ÎN RO"),
    AnimeItem("Kuroko", "2012", "Sport • Dramă", "O echipă de baschet încearcă să ajungă în vârful competiției.", "VERIFICĂ ÎN RO"),
    AnimeItem("Castlevania", "2017", "Fantasy • Acțiune", "Un vânător de vampiri luptă împotriva forțelor întunericului.", "VERIFICĂ ÎN RO"),
    AnimeItem("BAKI", "2018", "Acțiune • Arte marțiale", "Baki își testează limitele în confruntări extreme.", "VERIFICĂ ÎN RO"),
    AnimeItem("Hajime no Ippo: Lupta", "2000", "Sport • Dramă", "Un adolescent timid descoperă boxul și începe să se antreneze.", "VERIFICĂ ÎN RO"),
    AnimeItem("Căsnicia mea fericită", "2023", "Dramă • Fantasy", "O tânără descoperă iubirea și o lume plină de puteri misterioase.", "VERIFICĂ ÎN RO"),
    AnimeItem("Kakegurui", "2017", "Thriller • Dramă", "Într-o academie neobișnuită, statutul elevilor depinde de jocuri de noroc.", "VERIFICĂ ÎN RO"),
    AnimeItem("Baki Hanma", "2021", "Acțiune • Arte marțiale", "Baki se pregătește pentru cea mai grea confruntare a sa.", "VERIFICĂ ÎN RO"),
    AnimeItem("Parasyte: The Maxim", "2014", "SF • Horror", "Un adolescent împarte propriul corp cu o creatură extraterestră.", "VERIFICĂ ÎN RO"),
    AnimeItem("Ranma 1/2", "2024", "Comedie • Arte marțiale", "Un luptător este prins într-un blestem cu efecte neașteptate.", "VERIFICĂ ÎN RO"),
    AnimeItem("Viața dezastruoasă a lui Saiki K.", "2016", "Comedie • Supranatural", "Un adolescent cu puteri psihice vrea doar o viață normală.", "VERIFICĂ ÎN RO"),
    AnimeItem("Kengan Ashura", "2019", "Acțiune • Arte marțiale", "Luptători reprezintă companii în turnee brutale.", "VERIFICĂ ÎN RO"),
    AnimeItem("BEASTARS", "2019", "Dramă • Mister", "Un lup încearcă să-și înțeleagă instinctele într-o societate tensionată.", "VERIFICĂ ÎN RO"),
    AnimeItem("Monster", "2004", "Thriller • Mister", "Un medic pornește în căutarea unui fost pacient devenit periculos.", "VERIFICĂ ÎN RO"),
    AnimeItem("Komi nu știe să comunice", "2021", "Comedie • Romance", "O elevă timidă încearcă să-și facă o sută de prieteni.", "VERIFICĂ ÎN RO"),
    AnimeItem("Ouran High School Host Club", "2006", "Comedie • Romance", "O elevă ajunge într-un club școlar cu reguli neobișnuite.", "VERIFICĂ ÎN RO"),
    AnimeItem("The Summer Hikaru Died", "2025", "Mister • Supranatural", "Un adolescent observă că prietenul său s-a întors schimbat.", "VERIFICĂ ÎN RO"),
    AnimeItem("Delicious in Dungeon", "2024", "Fantasy • Aventură", "Un grup de aventurieri explorează o temniță și gătește creaturi fantastice.", "VERIFICĂ ÎN RO"),
    AnimeItem("Neon Genesis Evangelion", "1995", "SF • Dramă", "Adolescenți pilotează roboți uriași pentru a apăra omenirea.", "VERIFICĂ ÎN RO"),
    AnimeItem("Yu Yu Hakusho", "1992", "Acțiune • Supranatural", "Un adolescent devine detectiv al lumii spiritelor.", "VERIFICĂ ÎN RO"),
    AnimeItem("Regele șaman", "2021", "Aventură • Fantasy", "Șamanii se pregătesc pentru un turneu care decide Regele Șaman.", "VERIFICĂ ÎN RO"),
    AnimeItem("Violet Evergarden", "2018", "Dramă • Fantasy", "O fostă soldată învață să înțeleagă emoțiile oamenilor prin scrisori.", "VERIFICĂ ÎN RO"),
    AnimeItem("Cowboy Bebop", "1998", "SF • Aventură", "Vânători de recompense călătoresc prin spațiu.", "VERIFICĂ ÎN RO"),
    AnimeItem("Dorohedoro", "2020", "Fantasy • Acțiune", "Un bărbat cu cap de reptilă caută adevărul despre transformarea sa.", "VERIFICĂ ÎN RO"),
    AnimeItem("Aggretsuko", "2018", "Comedie • Viață", "O angajată își descarcă frustrările prin karaoke metal.", "VERIFICĂ ÎN RO"),
    AnimeItem("PLUTO", "2023", "SF • Mister", "Un detectiv robot investighează o serie de crime misterioase.", "VERIFICĂ SUBTITRAREA"),
    AnimeItem("Kotaro locuiește singur", "2022", "Comedie • Dramă", "Un băiețel neobișnuit schimbă viața vecinilor săi.", "VERIFICĂ ÎN RO"),
    AnimeItem("Blue Period", "2021", "Dramă • Artă", "Un elev descoperă pasiunea pentru pictură și intră într-o lume competitivă.", "VERIFICĂ ÎN RO"),
    AnimeItem("EDENS ZERO", "2021", "SF • Aventură", "Un tânăr pornește într-o călătorie prin univers.", "VERIFICĂ ÎN RO"),
    AnimeItem("Unchiul dintr-o altă lume", "2022", "Comedie • Fantasy", "Un bărbat se trezește după ani de comă cu amintiri dintr-o lume fantastică.", "VERIFICĂ ÎN RO"),
    AnimeItem("SPRIGGAN", "2022", "Acțiune • SF", "Agenți speciali protejează relicve ale unei civilizații antice.", "VERIFICĂ ÎN RO"),
    AnimeItem("Onmyoji", "2023", "Fantasy • Supranatural", "Un maestru al spiritelor rezolvă mistere în Japonia imperială.", "VERIFICĂ ÎN RO"),
    AnimeItem("My Daemon", "2023", "Aventură • SF", "Un băiat și o creatură specială pornesc într-o călătorie.", "VERIFICĂ ÎN RO"),
    AnimeItem("Superescrocii", "2021", "Acțiune • Crimă", "Un grup de infractori cu puteri speciale pregătește o lovitură.", "VERIFICĂ ÎN RO"),
    AnimeItem("Viața unui casnic", "2021", "Comedie • Viață", "Un fost membru yakuza se ocupă acum de treburile casei.", "VERIFICĂ ÎN RO"),
    AnimeItem("Leviathan", "2025", "SF • Aventură", "O poveste alternativă despre război și mașinării extraordinare.", "VERIFICĂ ÎN RO"),
    AnimeItem("GAMERA: Renașterea", "2023", "Acțiune • SF", "Copiii întâlnesc o creatură gigantică ce apără omenirea.", "VERIFICĂ ÎN RO"),
    AnimeItem("Noapte bună, lume!", "2023", "SF • Dramă", "O familie fragmentată se intersectează într-o lume virtuală.", "VERIFICĂ ÎN RO"),
    AnimeItem("Castlevania: Nocturne", "2023", "Fantasy • Acțiune", "Un vânător de vampiri se ridică împotriva unei noi amenințări.", "VERIFICĂ ÎN RO"),
    AnimeItem("DOTA: Sângele dragonului", "2021", "Fantasy • Acțiune", "Un cavaler dragon este prins într-un conflict între forțe magice.", "VERIFICĂ ÎN RO"),
    AnimeItem("Black Clover: Sabia Regelui Vrăjitor", "2023", "Fantasy • Film", "Asta și prietenii săi se confruntă cu foști regi vrăjitori.", "VERIFICĂ ÎN RO"),
    AnimeItem("Prințesa Mononoke", "1997", "Fantasy • Film", "Un tânăr ajunge în mijlocul conflictului dintre oameni și natură.", "VERIFICĂ ÎN RO"),
    AnimeItem("Băiatul și stârcul", "2023", "Fantasy • Film", "Un băiat descoperă o lume misterioasă după o pierdere.", "VERIFICĂ ÎN RO"),
    AnimeItem("Ponyo", "2008", "Fantasy • Film", "O creatură marină visează să devină om.", "VERIFICĂ ÎN RO"),
    AnimeItem("Vecinul meu Totoro", "1988", "Fantasy • Film", "Două surori descoperă spirite magice în apropierea casei.", "VERIFICĂ ÎN RO"),
    AnimeItem("Kiki: Livrări la domiciliu", "1989", "Aventură • Film", "O tânără vrăjitoare își începe viața independentă.", "VERIFICĂ ÎN RO"),
    AnimeItem("Povestea prințesei Kaguya", "2013", "Dramă • Film", "O fetiță misterioasă crește și își caută propriul drum.", "VERIFICĂ ÎN RO"),
    AnimeItem("Lumea secretă a lui Arrietty", "2010", "Aventură • Film", "O fetiță minusculă trăiește ascunsă în casa oamenilor.", "VERIFICĂ ÎN RO"),
    AnimeItem("Regatul pisicilor", "2002", "Fantasy • Film", "O adolescentă ajunge într-un regat fantastic al pisicilor.", "VERIFICĂ ÎN RO"),
    AnimeItem("Șoapte din inimă", "1995", "Dramă • Film", "O adolescentă își descoperă pasiunea pentru povești și scris.", "VERIFICĂ ÎN RO"),
    AnimeItem("Mary și floarea vrăjitoarei", "2017", "Fantasy • Film", "O fată descoperă o floare care îi oferă puteri magice.", "VERIFICĂ ÎN RO"),
    AnimeItem("Cosmica prințesă Kaguya!", "2026", "SF • Muzical • Film", "O fată misterioasă intră într-o lume virtuală plină de muzică.", "VERIFICĂ SUBTITRAREA"),
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

private val bg = Color(0xFF100509)
private val panel = Color(0xFF1B0B10)
private val accent = Color(0xFFE51B32)
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
    var selectedAnime by remember { mutableStateOf<AnimeItem?>(null) }
    var showUrlDialog by remember { mutableStateOf(false) }
    var showPlayer by remember { mutableStateOf(false) }
    var urlInput by remember { mutableStateOf("") }
    var urlError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val categories = listOf("Toate", "Acțiune", "Aventură", "Fantasy", "Filme")

    val filtered = catalog.filter { item ->
        val matchesQuery = item.title.contains(query, ignoreCase = true) ||
            item.genres.contains(query, ignoreCase = true)
        val matchesCategory = selected == "Toate" ||
            (selected == "Filme" && (item.badge == "FILM" || item.genres.contains("Film", ignoreCase = true))) ||
            item.genres.contains(selected, ignoreCase = true)
        matchesQuery && matchesCategory && (!showFavorites || favorites.contains(item.title))
    }

    MaterialTheme(colorScheme = darkColorScheme(
        background = bg, surface = panel, primary = accent,
        onBackground = Color.White, onSurface = Color.White
    )) {
        if (showPlayer && selectedAnime != null) {
            AnimePlayerScreen(
                title = selectedAnime!!.title,
                url = urlInput,
                onBack = { showPlayer = false }
            )
        } else if (selectedAnime != null) {
            AnimeDetailScreen(
                anime = selectedAnime!!,
                onBack = { selectedAnime = null },
                onPlay = { urlInput = ""; urlError = false; showUrlDialog = true }
            )
        } else {
        Column(Modifier.fillMaxSize().background(bg).padding(horizontal = 34.dp, vertical = 22.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("ANIME", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                Text(" RO", color = accent, fontSize = 30.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.width(18.dp))
                Text("UNIVERSUL ANIME, ÎNTR-UN SINGUR LOC", color = muted, fontSize = 11.sp)
                Spacer(Modifier.weight(1f))
                Text("v1.0.1", color = muted, fontSize = 12.sp)
            }
            Spacer(Modifier.height(18.dp))
            Text("Descoperă următorul tău anime.", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text("Catalog extins • caută disponibilitatea și subtitrarea în română", color = muted, fontSize = 14.sp)
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
                        selectedAnime = item
                    }
                }
                if (filtered.isEmpty()) item {
                    Text("Nu am găsit rezultate. Încearcă altă căutare.", color = muted, modifier = Modifier.padding(18.dp))
                }
            }
        }
        }
        if (showUrlDialog && selectedAnime != null) {
            AlertDialog(
                onDismissRequest = { showUrlDialog = false },
                title = { Text("Sursă video autorizată") },
                text = {
                    Column {
                        Text("Adaugă adresa directă a videoclipului sau a fluxului HLS (.m3u8) pe care ai dreptul să îl redai. Playerul va porni în ANIME RO.")
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = urlInput,
                            onValueChange = { urlInput = it; urlError = false },
                            label = { Text("Adresă video HTTPS") },
                            singleLine = true,
                            isError = urlError
                        )
                        if (urlError) Text("Introdu o adresă validă care începe cu https:// sau http://.", color = MaterialTheme.colorScheme.error)
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (urlInput.startsWith("https://") || urlInput.startsWith("http://")) {
                            showUrlDialog = false
                            showPlayer = true
                        } else urlError = true
                    }) { Text("Pornește playerul") }
                },
                dismissButton = {
                    TextButton(onClick = { showUrlDialog = false }) { Text("Anulează") }
                }
            )
        }
    }
}

@Composable
private fun AnimeDetailScreen(anime: AnimeItem, onBack: () -> Unit, onPlay: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(bg).padding(horizontal = 42.dp, vertical = 30.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = onBack) { Text("Înapoi") }
        Text(anime.title, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
        Text("${anime.year}  •  ${anime.genres}", color = muted, fontSize = 15.sp)
        Text(anime.description, color = Color(0xFFD8DEEA), fontSize = 17.sp)
        Spacer(Modifier.height(12.dp))
        Text("PLAYER ANIME RO", color = accent, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Text("Redare video direct în aplicație, cu suport pentru MP4 și HLS. Subtitrările incluse în flux sunt afișate de player.", color = muted, fontSize = 14.sp)
        Button(onClick = onPlay) { Text("Adaugă sursă și redă") }
        Text("Notă: catalogul nu conține încă fișiere video sau licențe de streaming. Folosește doar adrese de redare furnizate legal de titularul conținutului.", color = muted, fontSize = 12.sp)
    }
}

@Composable
private fun AnimePlayerScreen(title: String, url: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val player = remember(url) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = true
        }
    }
    DisposableEffect(player) {
        onDispose { player.release() }
    }
    Column(Modifier.fillMaxSize().background(Color.Black).padding(24.dp)) {
        TextButton(onClick = onBack) { Text("‹ Înapoi la ${title}", color = accent) }
        AndroidView(
            factory = { viewContext -> PlayerView(viewContext).apply { this.player = player; useController = true } },
            update = { it.player = player },
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
        Text("Player video ANIME RO", color = muted, fontSize = 12.sp)
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
            Button(onClick = onSearch) { Text("Detalii") }
        }
    }
}
