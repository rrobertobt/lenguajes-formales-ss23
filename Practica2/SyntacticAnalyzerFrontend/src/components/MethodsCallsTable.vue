<template>
  <v-data-table
    :headers="headers"
    :items="items"
    class="pb-4 rounded"
    hide-default-footer
    :loading="loading"
    :loading-text="'Esperando...'"
    :no-data-text="noDataText"
    :items-per-page="5"
    :items-per-page-options="[
      { value: 5, title: '5' },
      { value: 10, title: '10' },
      { value: -1, title: '$vuetify.dataFooter.itemsPerPageAll' }
    ]"
    density="comfortable"
    height="400px"
    width="100px"
  >
    <template #item.methodName="{ item }">
      <code class="table-lexeme">
        {{ item.columns.methodName }}
      </code>
    </template>
    <!-- <template #item.pattern="{ item }">
      <span class="table-pattern">
        {{ item.columns.pattern }}
      </span>
    </template>
    <template #item.type="{ item }">
      <span class="table-type">
        {{ item.columns.type }}
      </span>
    </template> -->
  </v-data-table>
</template>
<script>
export default {
  props: {
    methodsCallsTable: {
      type: Object,
      default: () => {}
    },
    loading: {
      type: Boolean,
      default: false
    },
    noDataText: {
      type: String,
      default: 'Sin datos'
    },
    showPattern: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {}
  },
  computed: {
    items() {
      return this.methodsCallsTable?.items
    },
    headers() {
      const normalHeaders = [
        { title: 'Metodo', key: 'methodName' },
        { title: 'Veces llamado', key: 'timesCalled' },
        { title: 'Linea ultima llamada', key: 'lastCalledAtLine' }
      ]
      return normalHeaders
    }
  }
}
</script>
<style scoped>
/* .table-lexeme {
  padding-left: 5px;
  padding-right: 5px;
  background-color: #dfdfdf;
  font-family: 'JetBrains Mono', monospace;
  font-size: 0.8rem;
  font-weight: 600;
}

.table-pattern {
  font-family: 'JetBrains Mono', monospace;
  font-size: 0.8rem;
}

.table-type {
  font-size: 0.8rem;
} */
</style>
