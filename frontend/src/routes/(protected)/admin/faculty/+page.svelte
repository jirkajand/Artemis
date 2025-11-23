<script lang="ts">
	import { GenderEnum, type RegisterLocalStudentRequest } from "$lib/api";
	import Button from "@smui/button";
    import Textfield from "@smui/textfield";
	import type { PageProps } from "../../$types";

    const { data }: PageProps = $props()
    const { clients } = data;
    const settingsClient = clients.settings;

    const formInitial = {
        facultyNameInternational: '',
        facultyNameLocal: '',
        color: '',
        shortName: ''
    }
  
    let form = $state(formInitial);
    let errorMessage = $state<string | null>(null);
    let successMessage = $state<string | null>(null);
    let loading = $state(false);

  async function submit(event: SubmitEvent & { currentTarget: EventTarget & HTMLFormElement}) {
    event.preventDefault();

    // 1) Basic validation
    errorMessage = null;
    if(!form.facultyNameInternational || !form.facultyNameLocal || !form.color || !form.shortName) {
      errorMessage = "All fields are required.";
      return;
    }

    // 2) Submit the form
    try {
        loading = true;
        
        const res = await settingsClient.createFaculty({
            facultyCreateRequest: form
        });
        successMessage = `Faculty ${form.facultyNameInternational} created successfully. ${Date.now().toString()}, ${res.id}`;
        
    } catch (error: any) {
        console.error("Failed to register:", error?.message);
        errorMessage = error?.message || 'An unknown error occurred.';
    } finally {
        loading = false;
    }
  }
</script>

<h1>Create Faculty</h1>

<form onsubmit={submit}>

    <section>
        <Textfield bind:value={form.facultyNameInternational} label="Faculty Name (International)" required />
        <Textfield bind:value={form.facultyNameLocal} label="Faculty Name (Local)" required />
        <Textfield bind:value={form.shortName} label="Short Name" required />
        <Textfield bind:value={form.color} label="Color (Hex Code)" required />
    </section>

  <Button type="submit" disabled={loading} variant="outlined">
    Create Faculty
  </Button>
</form>
{#if loading}
    <p>Submitting your faculty...</p>
{/if}
{#if errorMessage}
    <p style="color: red;">{errorMessage}</p>
{/if}
{#if successMessage}
    <p style="color: green;">{successMessage}</p>
{/if}

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