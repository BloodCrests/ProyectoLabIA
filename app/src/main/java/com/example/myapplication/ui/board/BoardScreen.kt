package com.example.myapplication.ui.board

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Pictograma

@Composable
fun BoardScreen(
    onHablar: (String) -> Unit,
    vm: BoardViewModel = viewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(12.dp)
    ) {
        BarraFrase(
            seleccion = vm.seleccion,
            onBorrarUltimo = vm::borrarUltimo,
            onLimpiar = vm::limpiar,
            onHablar = { if (vm.seleccion.isNotEmpty()) onHablar(vm.frase) }
        )

        Spacer(Modifier.height(12.dp))

        FilaCategorias(
            categorias = vm.categorias.map { it.id to it.nombre },
            actual = vm.categoriaActual,
            onElegir = vm::elegirCategoria
        )

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 110.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(vm.pictogramasVisibles, key = { it.id }) { p ->
                TarjetaPictograma(p, onClick = { vm.agregar(p) })
            }
        }
    }
}

@Composable
private fun BarraFrase(
    seleccion: List<Pictograma>,
    onBorrarUltimo: () -> Unit,
    onLimpiar: () -> Unit,
    onHablar: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(8.dp)) {
            // Pictogramas elegidos
            if (seleccion.isEmpty()) {
                Box(Modifier.fillMaxWidth().height(84.dp), contentAlignment = Alignment.Center) {
                    Text("Toca los pictogramas para armar tu frase")
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(84.dp)
                ) {
                    items(seleccion) { p -> MiniPictograma(p) }
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(onClick = onBorrarUltimo) {
                    Icon(Icons.Default.Clear, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Borrar")
                }
                FilledTonalButton(onClick = onLimpiar) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Limpiar")
                }
                Spacer(Modifier.weight(1f))
                Button(onClick = onHablar) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Hablar")
                }
            }
        }
    }
}

@Composable
private fun MiniPictograma(p: Pictograma) {
    Card(shape = RoundedCornerShape(10.dp)) {
        Column(
            Modifier.padding(4.dp).width(72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagenPictograma(p, Modifier.size(48.dp))
            Text(p.texto, fontSize = 13.sp, maxLines = 1)
        }
    }
}

@Composable
private fun FilaCategorias(
    categorias: List<Pair<String, String>>,
    actual: String,
    onElegir: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categorias) { (id, nombre) ->
            FilterChip(
                selected = id == actual,
                onClick = { onElegir(id) },
                label = { Text(nombre, fontSize = 16.sp) },
                modifier = Modifier.heightIn(min = 48.dp)
            )
        }
    }
}

@Composable
private fun TarjetaPictograma(p: Pictograma, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.fillMaxWidth().aspectRatio(1f)
    ) {
        Column(
            Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImagenPictograma(p, Modifier.weight(1f).fillMaxWidth())
            Text(p.texto, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        }
    }
}

@Composable
private fun ImagenPictograma(p: Pictograma, modifier: Modifier) {
    if (p.imagen != null) {
        AsyncImage(
            model = "file:///android_asset/pictogramas/${p.imagen}",
            contentDescription = p.texto,
            contentScale = ContentScale.Fit,
            modifier = modifier
        )
    } else {
        Spacer(modifier)
    }
}