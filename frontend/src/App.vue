<template>
  <div style="max-width: 750px; margin: 30px auto; padding: 0 20px; font-family: Arial, sans-serif;">

    <h2 style="color: #333;">Agendamento de Transferência</h2>

    <div v-if="alerta" style="color: red; margin-bottom: 10px;">{{ alerta }}</div>
    <div v-if="sucesso" style="color: green; margin-bottom: 10px;">{{ sucesso }}</div>

    <div style="background: #fff; padding: 20px; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 20px;">
      <div style="margin-bottom: 12px;">
        <label>Conta Origem:</label><br />
        <input v-model="form.contaOrigem" placeholder="XXXXXXXXXX" style="width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #ccc; border-radius: 4px;" />
      </div>

      <div style="margin-bottom: 12px;">
        <label>Conta Destino:</label><br />
        <input v-model="form.contaDestino" placeholder="XXXXXXXXXX" style="width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #ccc; border-radius: 4px;" />
      </div>

      <div style="margin-bottom: 12px;">
        <label>Valor (R$):</label><br />
        <input v-model="form.valor" type="number" placeholder="0.00" style="width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #ccc; border-radius: 4px;" />
      </div>

      <div style="margin-bottom: 16px;">
        <label>Data de Transferência:</label><br />
        <input v-model="form.dataTransferencia" type="date" style="width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #ccc; border-radius: 4px;" />
      </div>

      <button @click="agendar" style="background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px;">
        Agendar
      </button>
    </div>

    <h2 style="color: #333;">Extrato de Agendamentos</h2>

    <p v-if="agendamentos.length === 0">Nenhum agendamento cadastrado.</p>

    <table v-else border="1" cellpadding="8" cellspacing="0" style="width: 100%; border-collapse: collapse;">
      <thead style="background-color: #f2f2f2;">
        <tr>
          <th>Conta Origem</th>
          <th>Conta Destino</th>
          <th>Valor</th>
          <th>Taxa</th>
          <th>Data Transferência</th>
          <th>Data Agendamento</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="agendamento in agendamentos" :key="agendamento.id">
          <td>{{ agendamento.contaOrigem }}</td>
          <td>{{ agendamento.contaDestino }}</td>
          <td>R$ {{ agendamento.valor }}</td>
          <td>R$ {{ agendamento.taxa }}</td>
          <td>{{ agendamento.dataTransferencia }}</td>
          <td>{{ agendamento.dataAgendamento }}</td>
        </tr>
      </tbody>
    </table>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      form: {
        contaOrigem: '',
        contaDestino: '',
        valor: '',
        dataTransferencia: ''
      },
      agendamentos: [],
      alerta: '',
      sucesso: ''
    }
  },
  mounted() {
    this.listar()
  },
  methods: {
    async agendar() {
      this.alerta = ''
      this.sucesso = ''
      try {
        await axios.post('http://localhost:8080/agendamentos', this.form)
        this.sucesso = 'Agendamento realizado com sucesso!'
        this.listar()
      } catch (e) {
        this.alerta = e.response?.data || 'Erro ao realizar agendamento.'
      }
    },
    async listar() {
      const response = await axios.get('http://localhost:8080/agendamentos')
      this.agendamentos = response.data
    }
  }
}
</script>