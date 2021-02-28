/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            Surface(color = MaterialTheme.colors.background) {
                Scaffold(topBar = {
                    TopAppBar(
                        elevation = 4.dp,
                        navigationIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_pawprint),
                                contentDescription = "",
                                modifier = Modifier.padding(8.dp)
                            )
                        },
                        backgroundColor = Color.White,
                        title = {
                            Text("Home")
                        }
                    )
                }) {
                    BodyContent(navController, Modifier.padding(it))
                }
            }
        }
        composable("detail") {
            Surface(color = MaterialTheme.colors.background) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text("Detail")
                        }
                    )
                }) {
                    DetailContent(navController)
                }
            }

        }
    }
}

private var mAnimal: Animal? = null

@Composable
fun DetailContent(navController: NavController, modifier: Modifier = Modifier) {

    val animal = mAnimal ?: return

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {

        // Image
        Image(
            painter = painterResource(id = animal.photoId),
            contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = animal.name,
            style = typography.h6
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = if (animal.gender) "Male" else "Female")
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Size: ${animal.size}")
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Weight: ${animal.weight}")
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Age: ${animal.age}")
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Adoption Fee: ${animal.adoptionFee}")
        Spacer(modifier = Modifier.padding(8.dp))

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text("Additional Info: ${animal.detail}", style = MaterialTheme.typography.body2)
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        ) {
            Text(text = "Ask About ${animal.name}")
        }
    }

}

@Composable
fun BodyContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val list = mutableListOf<Animal>()
    list.add(
        Animal(
            "Pierre",
            age = "6 months",
            photoId = R.drawable.b,
            gender = true,
            detail = "Hi, my name is Pierre.",
            adoptionFee = "$400",
            size = "Medium",
            weight = "5 kg"
        )
    )
    list.add(
        Animal(
            "Anna",
            age = "6 months",
            photoId = R.drawable.c,
            gender = false,
            detail = "She is a beautiful girl.",
            adoptionFee = "$400",
            size = "Medium",
            weight = "5 kg"
        )
    )
    list.add(
        Animal(
            "Max",
            age = "6 months",
            photoId = R.drawable.d,
            gender = true,
            detail = "He has a friendly personality.",
            adoptionFee = "$400",
            size = "Medium",
            weight = "5 kg"
        )
    )
    list.add(
        Animal(
            "Lucy",
            age = "6 months",
            photoId = R.drawable.e,
            gender = false,
            detail = "She likes walking.",
            adoptionFee = "$400",
            size = "Medium",
            weight = "5 kg"
        )
    )
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(list) {
            AnimalListItem(navController, animal = it)
        }
    }
}

@Composable
fun AnimalListItem(navController: NavController, animal: Animal, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
//            .clickable { Log.d("HOGE", "click ${animal.name}") }
            .clickable {
                mAnimal = animal
                navController.navigate("detail")


            }
            .padding(16.dp)
    ) {

        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            // Image
            Image(
                painter = painterResource(id = animal.photoId),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = animal.name,
                style = typography.h6
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(animal.detail, style = MaterialTheme.typography.body2)
            }
        }
    }
}

class Animal(
    val name: String,
    val photoId: Int,
    val detail: String,
    val gender: Boolean,
    val age: String,
    val size: String,
    val adoptionFee: String,
    val weight: String
)

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
