import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-estado',
  templateUrl: './estado.component.html',
  styleUrls: ['./estado.component.css']
})
export class EstadoComponent implements OnInit {
  filtroEstado: string = ''; 
  estados: any[] = [];
  allEstados: any[] = [];
  selectedEstado: any;
  ordenacaoEstados: string = 'descricao';
  formularioVisivelEstado: boolean = false;
  estadoEditando: boolean = false;
  estadoIdEditando: number | null = null;
  estadoDescricao: string = '';

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.getEstados();
  }

  fecharFormularioEstado(){
    this.formularioVisivelEstado = false;
  }

  getEstados() {
    this.apiService.getEstados().subscribe(data => {
      this.estados = data;
    });
  }

  abrirFormularioEstado(estado?: any){
    if (estado) {
      this.estadoEditando = true;
      this.estadoIdEditando = estado.itemEstadoId;
      this.estadoDescricao = estado.descricao;
    } else {
      this.estadoEditando = false;
      this.estadoDescricao = '';
    }
    this.formularioVisivelEstado = true;
  }

  excluirEstados(estadoId: any){
    this.apiService.deleteEstado(estadoId).subscribe(() => {
      this.estados = this.estados.filter(estados => estados.itemEstadoId !== estadoId);
      this.allEstados = this.allEstados.filter(estados => estados.itemEstadoId !== estadoId);
    });
  }

  editarEstados(estado: any){
    this.estadoEditando = true;
    this.estadoDescricao = estado.descricao;
    this.selectedEstado = estado;
    this.abrirFormularioEstado(estado);
  }

  toggleItemsVisibility(id: number){

  }

  salvarEstado(){
    if (this.estadoEditando) {
      this.selectedEstado.descricao = this.estadoDescricao;
      this.updateEstado(this.selectedEstado);
      this.fecharFormularioEstado();
    } else {
      this.createEstado();
    }
  }

  updateEstado(estado: any) {
    this.apiService.updateEstado(estado).subscribe(() => {
      this.getEstados(); 
      this.fecharFormularioEstado();
    });
  }

  estadosFiltradasOrdenadas(): any[]{
    let estadosFiltradas = this.estados.filter(estado =>
      estado.descricao.toLowerCase().includes(this.filtroEstado.toLowerCase())
    );

    return estadosFiltradas.sort((a, b) => {
      return a[this.ordenacaoEstados].localeCompare(b[this.ordenacaoEstados]);
    });
  }

  createEstado() {
    if (this.estadoDescricao.trim()) {
      const newLista = {
        descricao: this.estadoDescricao
      };
      this.apiService.createEstado(newLista).subscribe(() => {
        this.getEstados();
        this.estadoDescricao = '';
        this.fecharFormularioEstado();
      });
    }
  }


}
