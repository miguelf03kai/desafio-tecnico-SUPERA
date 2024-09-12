import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-prioridade',
  templateUrl: './prioridade.component.html',
  styleUrls: ['./prioridade.component.css']
})
export class PrioridadeComponent implements OnInit {
  filtroPrioridade: string = ''; 
  prioridades: any[] = [];
  allPrioridades: any[] = [];
  selectedPrioridade: any;
  ordenacaoPrioridades: string = 'descricao';
  formularioVisivelPrioridade: boolean = false;
  prioridadeEditando: boolean = false;
  prioridadeIdEditando: number | null = null;
  prioridadeDescricao: string = '';
  prioridadeCor: string = '#ffffff';
  


  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.getPrioridades();
  }

  fecharFormularioPrioridade(){
    this.formularioVisivelPrioridade = false;
  }

  getPrioridades() {
    this.apiService.getPrioridades().subscribe(data => {
      this.prioridades = data;
    });
  }

  abrirFormularioPrioridade(prioridade?: any){
    if (prioridade) {
      this.prioridadeEditando = true;
      this.prioridadeIdEditando = prioridade.itemPrioridadeId;
      this.prioridadeDescricao = prioridade.descricao;
      this.prioridadeCor = prioridade.cor;
    } else {
      this.prioridadeEditando = false;
      this.prioridadeDescricao = '';
    }
    this.formularioVisivelPrioridade = true;
  }

  excluirPrioridades(prioridadeId: any){
    this.apiService.deletePrioridade(prioridadeId).subscribe(() => {
      this.prioridades = this.prioridades.filter(prioridades => prioridades.itemPrioridadeId !== prioridadeId);
      this.allPrioridades = this.allPrioridades.filter(prioridades => prioridades.itemPrioridadeId !== prioridadeId);
    });
  }

  editarPrioridades(prioridade: any){
    this.prioridadeEditando = true;
    this.prioridadeDescricao = prioridade.descricao;
    this.selectedPrioridade = prioridade;
    this.abrirFormularioPrioridade(prioridade);
  }

  toggleItemsVisibility(id: number){

  }

  salvarPrioridade(){
    if (this.prioridadeEditando) {
      this.selectedPrioridade.descricao = this.prioridadeDescricao;
      this.selectedPrioridade.cor = this.prioridadeCor;
      this.updatePrioridade(this.selectedPrioridade);
      this.fecharFormularioPrioridade();
    } else {
      this.createPrioridade();
    }
  }

  updatePrioridade(prioridade: any) {
    this.apiService.updatePrioridade(prioridade).subscribe(() => {
      this.getPrioridades(); 
      this.fecharFormularioPrioridade();
    });
  }

  prioridadesFiltradasOrdenadas(): any[]{
    let prioridadesFiltradas = this.prioridades.filter(prioridade =>
      prioridade.descricao.toLowerCase().includes(this.filtroPrioridade.toLowerCase())
    );

    return prioridadesFiltradas.sort((a, b) => {
      return a[this.ordenacaoPrioridades].localeCompare(b[this.ordenacaoPrioridades]);
    });
  }

  createPrioridade() {
    if (this.prioridadeDescricao.trim()) {
      const newLista = {
        descricao: this.prioridadeDescricao,
        cor: this.prioridadeCor 
      };
      this.apiService.createPrioridade(newLista).subscribe(() => {
        this.getPrioridades();
        this.prioridadeDescricao = '';
        this.prioridadeCor = '#ffffff';
        this.fecharFormularioPrioridade();
      });
    }
  }

}
