<script lang="ts">
	import { GenderEnum, type FacultyResponse, type RegisterLocalStudentRequest } from "$lib/api";
	import Button from "@smui/button";
  import Textfield from "@smui/textfield";
  import Select, { Option } from "@smui/select";
  import Checkbox from "@smui/checkbox";
  import FormField from "@smui/form-field";
  import TabBar from "@smui/tab-bar";
  import Tab, { Label } from "@smui/tab";
	import { goto } from "$app/navigation";
	import type { PageProps } from "../$types";
	import { onMount } from "svelte";
	import PhoneNumberInput from "$lib/components/registration/PhoneNumberInput.svelte";

  const { data }:PageProps = $props()
  const { clients } = data;
  const userManagementClient = clients.management;
  const settingsClient = clients.settings;

  const formInitial: Omit<RegisterLocalStudentRequest, 'dateOfBirth'> & {
    dateOfBirth: string
  } = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    phoneNumber: '',
    facultyId: '',
    dateOfBirth: '',
    gender: GenderEnum.Other,
    termsAndConditionsChecked: false
  }
  
  let form = $state(formInitial);
  let confirmPassword = $state('');
  let errorMessage = $state<string | null>(null);
  let loading = $state(false);

  let facultyList = $state<FacultyResponse[]>([]);

  onMount(async () => {
    await loadFaculties();
  });

  async function loadFaculties() {
    facultyList = await settingsClient.getAllFaculties();
  }

  async function submit(event: SubmitEvent & { currentTarget: EventTarget & HTMLFormElement}) {
    event.preventDefault();

    // 1) Basic validation
    errorMessage = null;
    if (form.password !== confirmPassword) {
      errorMessage = "Passwords do not match.";
      return;
    }
    if (!form.termsAndConditionsChecked) {
      errorMessage = "You must agree to the terms and conditions.";
      return;
    }

    // 2) Submit the form
    try {
      loading = true;
      const updatedForm: RegisterLocalStudentRequest = {
        ...form,
        dateOfBirth: new Date(form.dateOfBirth),
      }

      const res = await userManagementClient.registerLocalStudent({
        registerLocalStudentRequest: updatedForm
      });

      await goto('/register/success');
    } catch (error: any) {
      console.error("Failed to register:", error?.message);
      errorMessage = error?.message || 'An unknown error occurred.';
    } finally {
      loading = false;
    }
  }
</script>

<TabBar tabs={['International Student', 'Member/Buddy']} active={'Member/Buddy'}>
    {#snippet tab(tab)}
      <Tab {tab} onclick={() => goto(tab === 'Member/Buddy' ? '/register/local' : '/register/international')}>
        <Label>{tab}</Label>
      </Tab>
    {/snippet}
</TabBar>

<h1>Register - Member</h1>

<form onsubmit={submit}>

  <section>
    <Textfield bind:value={form.firstName} style="width: 100%;" label="First Name" required />
    <Textfield bind:value={form.lastName} style="width: 100%;" label="Last Name" required />
    <Textfield bind:value={form.email} style="width: 100%;" label="Email" type="email" required />
  </section>

  <section>
    <PhoneNumberInput bind:value={form.phoneNumber} label="Phone Number" required />
    <Select bind:value={form.gender} label="Gender" required>
      <Option value={GenderEnum.Male}>Male</Option>
      <Option value={GenderEnum.Female}>Female</Option>
      <Option value={GenderEnum.Other}>Other</Option>
    </Select>
    <Select bind:value={form.facultyId} label="Faculty" required>
      {#each facultyList as faculty}
        <Option value={faculty.id}>{faculty.shortName}</Option>
      {/each}
    </Select>
  </section>

  <section>
    <Textfield bind:value={form.password} style="width: 100%;" label="Password" type="password" required />
    <Textfield bind:value={confirmPassword} style="width: 100%;" label="Confirm Password" type="password" required />
    <Textfield bind:value={form.dateOfBirth} style="width: 100%;" label="Date of Birth" type="date" required />
  </section>

  <FormField>
    <Checkbox bind:checked={form.termsAndConditionsChecked} />
      {#snippet label()}
        I confirm that I have read the <a href="/privacy-policy">Privacy Policy</a> and I agree to the <a href="/terms-and-conditions">Terms and Conditions</a>.
      {/snippet}
  </FormField>
  <p>
    After submitting the form you need to wait for the <strong>account verification</strong>.
    You will receive an email once your account is verified.
  </p>

  <Button type="submit" disabled={loading} variant="outlined">
    Become a member
  </Button>
</form>
{#if loading}
  <p>Submitting your registration...</p>
{/if}
{#if errorMessage}
  <p style="color: var(--error);">{errorMessage}</p>
{/if}

<a href="/login">Back to login</a>

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