import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListaComponent } from './components/lista/lista.component'; 
import { PrioridadeComponent } from './components/prioridade/prioridade.component'; 
import { EstadoComponent } from './components/estado/estado.component'; 

const routes: Routes = [
  { path: '', redirectTo: '/lista', pathMatch: 'full' },
  { path: 'lista', component: ListaComponent },
  { path: 'prioridade', component: PrioridadeComponent },
  { path: 'estado', component: EstadoComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }