import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.unieventos.model.Event

@Composable
fun ItemEvento(
    event: Event,
    color: Color = MaterialTheme.colorScheme.primary,
    onNavigationToEventDetail: (Int) -> Unit
){

Card (
    modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    onClick = {
        onNavigationToEventDetail(event.id)
    }
){
 Column {

     val model = ImageRequest.Builder(LocalContext.current)
         .data(event.image)
         .crossfade(true)
         .build()

     AsyncImage(
         modifier = Modifier
             .fillMaxWidth()
             .height(150.dp),
         model = model ,
         contentDescription = "",
         contentScale = ContentScale.Crop)
     Spacer(modifier = Modifier.height(20.dp))

 Text(
     text = event.title,
     modifier = Modifier
         .fillMaxWidth()
     ,
     fontSize = 16.sp,
     fontWeight = FontWeight.Bold,
     textAlign = TextAlign.Center
 )
     Spacer(modifier = Modifier.height(20.dp))

     Row(
         modifier = Modifier
             .fillMaxWidth(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.Center
     ) {
         Text(
             text = event.date,
         )
         Spacer(modifier = Modifier.width(20.dp))

         Text(
             text = event.city,
         )
     }

     Spacer(modifier = Modifier.height(20.dp))

 }
}

}