import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css']
})
export class ListaComponent implements OnInit {
  listas: any[] = []; 
  itens: any[] = []; 
  allItens: any[] = []; 
  estados: any[] = [];
  prioridades: any[] = []; 
  selectedLista: any; 
  filtroLista: string = ''; 
  filtroItem: string = ''; 
  ordenacaoListas: string = 'descricao';
  ordenacaoItens: string = 'descricao'; 

  
  formularioVisivelLista: boolean = false;
  formularioVisivelItem: boolean = false;
  listaEditando: boolean = false;
  itemEditando: boolean = false;

  
  listaDescricao: string = '';
  itemDescricao: string = '';
  itemEstadoId: number | null = null;
  itemPrioridadeId: number | null = null;
  itemIdEditando: number | null = null;

  listaIdEditando: number | null = null;

  itemsVisibility: Map<number, boolean> = new Map();

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.getListas();
    this.getEstados();
    this.getPrioridades();
    this.getAllItens();
  }

  getListas() {
    this.apiService.getListas().subscribe(data => {
      this.listas = data;
    });
  }

  getEstados() {
    this.apiService.getEstados().subscribe(data => {
      this.estados = data;
    });
  }

  getPrioridades() {
    this.apiService.getPrioridades().subscribe(data => {
      this.prioridades = data;
    });
  }

  getAllItens() {
    this.apiService.getItens().subscribe(data => {
      this.allItens = data;
    });
  }

  listasFiltradasOrdenadas(): any[] {
    let listasFiltradas = this.listas.filter(lista =>
      lista.descricao.toLowerCase().includes(this.filtroLista.toLowerCase())
    );

    return listasFiltradas.sort((a, b) => {
      return a[this.ordenacaoListas].localeCompare(b[this.ordenacaoListas]);
    });
  }

  itensFiltradosOrdenados(): any[] {
    let itensFiltrados = this.itens.filter(item =>
      item.descricao.toLowerCase().includes(this.filtroItem.toLowerCase())
    );
  
    return itensFiltrados.sort((a, b) => {
      const valorA = a[this.ordenacaoItens] || '';
      const valorB = b[this.ordenacaoItens] || '';
      return valorA.localeCompare(valorB);
    });
  }

  selectLista(lista: any) {
    this.selectedLista = lista;
    this.itens = this.allItens.filter(item => item.listaId === lista.listaId);
  }

  abrirFormularioLista(lista?: any) {
    if (lista) {
      this.listaEditando = true;
      this.listaIdEditando = lista.listaId;
      this.itemDescricao = lista.descricao;
    } else {
      this.listaEditando = false;
    this.listaDescricao = '';
    }
    this.formularioVisivelLista = true;
  }

  fecharFormularioLista() {
    this.formularioVisivelLista = false;
  }

  abrirFormularioItem(item?: any) {
    if (item) {
      this.itemEditando = true;
      this.itemIdEditando = item.itemId;
      this.itemDescricao = item.descricao;
      this.itemPrioridadeId = item.itemPrioridadeId;
      this.itemEstadoId = item.itemEstadoId;
    } else {
      this.itemEditando = false;
      this.limparFormularioItem();
    }
    this.formularioVisivelItem = true;
  }

  fecharFormularioItem() {
    this.formularioVisivelItem = false;
  }

  limparFormularioItem() {
    this.itemDescricao = '';
    this.itemPrioridadeId = null;
    this.itemEstadoId = null;
    this.itemIdEditando = null;
  }

  salvarLista() {
    if (this.listaEditando) {
      this.selectedLista.descricao = this.listaDescricao;

      this.selectedLista
      this.updateLista(this.selectedLista);
    } else {
      this.createLista();
    }
  }

  salvarItem() {
    if (this.itemEditando) {
      this.updateItem();
    } else {
      this.createItem();
    }
  }

  createLista() {
    if (this.listaDescricao.trim()) {
      const newLista = { descricao: this.listaDescricao };
      this.apiService.createLista(newLista).subscribe(() => {
        this.getListas();
        this.listaDescricao = '';
        this.fecharFormularioLista();
      });
    }
  }

  updateLista(lista: any) {
    this.apiService.updateLista(lista).subscribe(() => {
      this.getListas(); 
      this.fecharFormularioLista();
    });
  }

  excluirLista(lista: any) {
    const itensAssociados = this.allItens.filter(item => item.listaId === lista.listaId);

    if (itensAssociados.length > 0) {
      alert('Não é possível excluir a lista porque ela contém itens. Exclua os itens primeiro.');
      return;
    }

    this.apiService.deleteLista(lista.listaId).subscribe(() => {
      this.listas = this.listas.filter(l => l.listaId !== lista.listaId);
      if (this.selectedLista && this.selectedLista.listaId === lista.listaId) {
        this.selectedLista = null;
        this.itens = [];
      }
    });
  }

  createItem() {
    if (!this.selectedLista || !this.itemDescricao.trim() || this.itemPrioridadeId === null || this.itemEstadoId === null) {
      alert('Todos os campos são obrigatórios para criar um item.');
      return;
    }

    const newItem = {
      descricao: this.itemDescricao,
      listaId: this.selectedLista.listaId,
      itemPrioridadeId: Number(this.itemPrioridadeId),
      itemEstadoId: Number(this.itemEstadoId)
    };
    
    this.apiService.createItem(newItem).subscribe((createdItem: any) => {
      this.allItens.push(createdItem);
      this.selectLista(this.selectedLista);
      this.limparFormularioItem();
      this.fecharFormularioItem();
    });
  }

  updateItem() {
    if (this.itemIdEditando !== null && this.itemDescricao.trim() && this.itemPrioridadeId !== null && this.itemEstadoId !== null) {
      const updatedItem = {
        itemId: this.itemIdEditando,
        descricao: this.itemDescricao,
        listaId: this.selectedLista.listaId,
        itemPrioridadeId: Number(this.itemPrioridadeId),
        itemEstadoId: Number(this.itemEstadoId)
      };

      this.apiService.updateItem(updatedItem).subscribe(() => {
        this.getAllItens();
        this.fecharFormularioItem();
        this.toggleItemsVisibility(updatedItem.listaId);
      });
    } else {
      alert('Todos os campos são obrigatórios para editar um item.');
    }
  }

  excluirItem(itemId: number) {
    this.apiService.deleteItem(itemId).subscribe(() => {
      this.itens = this.itens.filter(item => item.itemId !== itemId);
      this.allItens = this.allItens.filter(item => item.itemId !== itemId);
    });
  }

  toggleItemsVisibility(listaId: number) {
    const currentVisibility = this.itemsVisibility.get(listaId);
    this.itemsVisibility.set(listaId, !currentVisibility);
    if (!currentVisibility) {
      this.selectLista(this.listas.find(lista => lista.listaId === listaId));
    }
  }

  getPrioridadeDescricao(id: number): string {
    const prioridade = this.prioridades.find(p => p.itemPrioridadeId === id);
    return prioridade ? prioridade.descricao : 'Desconhecida';
  }

  getEstadoDescricao(id: number): string {
    const estado = this.estados.find(e => e.itemEstadoId === id);
    return estado ? estado.descricao : 'Desconhecido';
  }

  getPrioridadeCor(id: number): string {
    const prioridade = this.prioridades.find(p => p.itemPrioridadeId === id);
    return prioridade ? prioridade.cor : '#ffffff';
  }

  editarItem(item: any) {
    this.abrirFormularioItem(item);
  }

  editarLista(lista: any) {
    this.listaEditando = true;
    this.listaDescricao = lista.descricao;
    this.selectedLista = lista;
    this.abrirFormularioLista(lista);
  }
}
