package de.kilianeller.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ComposeContent() {
    val mainNavController = rememberNavController()

    NavHost(
        navController = mainNavController,
        startDestination = "MainDestination1",
    ) {

        composable("MainDestination1") {

            val sub1NavController = rememberNavController()

            NavHost(
                navController = sub1NavController,
                startDestination = "Sub1Destination1",
            ) {

                composable("Sub1Destination1") {
                    NavContent(
                        destination = "MainDestination1 - Sub1Destination1",
                        forward = "MainDestination2 - Sub1Destination1",
                        backward = "null",
                        onForward = {
                            sub1NavController.navigate("Sub1Destination2")
                        },
                        onBackward = {
                            //no previous destination in this graph, go back in parent
                            mainNavController.popBackStack()
                        }
                    )
                }


                composable("Sub1Destination2") {
                    NavContent(
                        destination = "MainDestination1 - Sub1Destination2",
                        forward = "MainDestination2 - Sub2Destination1",
                        backward = "MainDestination1 - Sub1Destination2",
                        onForward = {
                            //no more destinations go forward in parent graph
                            mainNavController.navigate("MainDestination2")
                        },
                        onBackward = {
                            sub1NavController.popBackStack()
                        }
                    )
                }

            }

        }

        composable("MainDestination2") {

            val sub2NavController = rememberNavController()

            NavHost(
                navController = sub2NavController,
                startDestination = "Sub2Destination1",
            ) {

                composable("Sub2Destination1") {
                    NavContent(
                        destination = "MainDestination2 - Sub2Destination1",
                        forward = "MainDestination2 - Sub2Destination2",
                        backward = "MainDestination1 - Sub1Destination2",
                        onForward = {
                            //no more destinations
                            sub2NavController.navigate("Sub2Destination2")
                        },
                        onBackward = {
                            //no previous destination in this graph, go back in parent
                            mainNavController.popBackStack()
                        }
                    )
                }


                composable("Sub2Destination2") {
                    NavContent(
                        destination = "MainDestination2 - Sub2Destination2",
                        forward = "null",
                        backward = "MainDestination2 - Sub2Destination2",
                        onForward = {
                            //no more destinations
                        },
                        onBackward = {
                            sub2NavController.popBackStack()
                        }
                    )
                }

            }

        }

    }

}

@Composable
fun NavContent(
    destination: String,
    forward: String,
    backward: String,
    onForward: () -> Unit,
    onBackward: () -> Unit,
) {


    Column(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text("current destination: $destination", color = Color.White)

        ElevatedButton(
            onClick = onForward
        ) {
            Text("go forward to $forward")
        }

        ElevatedButton(
            onClick = onBackward
        ) {
            Text("go back to $backward")
        }

    }

}