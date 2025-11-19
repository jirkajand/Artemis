<script lang="ts">
	import { GenderEnum, type RegisterLocalStudentRequest } from "$lib/api";
	import Button from "@smui/button";
  import Textfield from "@smui/textfield";
  import Select, { Option } from "@smui/select";
  import Checkbox from "@smui/checkbox";
  import FormField from "@smui/form-field";

  const { data } = $props()
  const { clients } = data;
  const userManagementClient = clients.management;

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

  async function submit() {
    try {
      const updatedForm = {
        ... form,
        dateOfBirth: new Date(form.dateOfBirth),
        gender: form.gender as GenderEnum
      }

      console.log("Submitting registration form:", updatedForm);

      // const res = await userManagementClient.registerLocalStudent({
      //   registerLocalStudentRequest: updatedForm
      // });
    } catch (error: any) {
      console.error("Failed to register:", error?.message);
    }
  }
</script>

<h1>Register - member</h1>

<form onsubmit={submit}>

  <section>
    <Textfield bind:value={form.firstName} style="width: 100%;" label="First Name" required />
    <Textfield bind:value={form.lastName} style="width: 100%;" label="Last Name" required />
    <Textfield bind:value={form.email} style="width: 100%;" label="Email" type="email" required />
  </section>

  <section>
    <Textfield bind:value={form.phoneNumber} label="Phone Number" required />
    <Select bind:value={form.gender} label="Gender">
      <Option value={GenderEnum.Male}>Male</Option>
      <Option value={GenderEnum.Female}>Female</Option>
      <Option value={GenderEnum.Other}>Other</Option>
    </Select>
    <Textfield bind:value={form.facultyId} style="width: 100%;" label="Faculty ID" required />
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

  <Button type="submit">
    Become a member
  </Button>
</form>

<a href="/login">Back to login</a>

<style>
  form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    max-width: 400px;
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