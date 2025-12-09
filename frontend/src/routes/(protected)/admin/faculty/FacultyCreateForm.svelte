<script lang="ts">
	import type { FacultyCreateRequest } from "$lib/api";
	import Textfield from "@smui/textfield";

    let { 
        form = $bindable()
    }: {
        form: FacultyCreateRequest;
    } = $props();
</script>

<form>
    <section>
        <Textfield bind:value={form.facultyNameInternational} label="Faculty Name (International)" required />
        <Textfield bind:value={form.facultyNameLocal} label="Faculty Name (Local)" required />
    </section>
    <section>
        <Textfield bind:value={form.shortName} label="Short Name" required />
        <Textfield bind:value={form.color} type="color" label="Color (Hex Code)" required />
    </section>
</form>

<style>
  form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    max-width: 400px;
    margin: 1rem 0;
  }

  form section {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  @media (min-width: 550px) {
    form {
      max-width: 850px;
    }
    form section {
      display: grid;
      grid-template-columns: repeat(3, minmax(0, 1fr));
      gap: 1rem;
    }
  }
</style>