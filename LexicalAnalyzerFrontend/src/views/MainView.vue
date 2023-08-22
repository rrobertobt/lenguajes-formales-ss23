<template>
  <header>
    <h1>Analizador léxico <code>"parser-py"</code> - Lenguajes Formales SS 2023</h1>
  </header>
  <v-divider></v-divider>
  <section class="main-section">
    Aqui va el editor
    <div class="table-area">Aqui deberia ir una tabla con los tokens</div>
  </section>
</template>

<script>
export default {}
</script>

<style scoped>
.main-section {
  max-width: 1900px;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 2rem;
  padding: 1rem;
}

.table-area {
  width: 50%;
  max-height: 800px;
}
header h1 {
  text-align: center;
}
</style>
