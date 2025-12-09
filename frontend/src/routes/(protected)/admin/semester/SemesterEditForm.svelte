<script lang="ts">
    import Select, { Option } from "@smui/select";
    import Textfield from '@smui/textfield';
    import type { SettingsServiceApi, CompleteInternationalStudentProfileRequest, SemesterCreateRequest } from '$lib/api';

  const formInitialValue: Omit<SemesterCreateRequest, 'semesterRegisterOpenDate'> & {
    semesterRegisterOpenDate: string;
  } = {
    semesterName: 'Semester 1',
    semesterType: 'WINTER',
    year: new Date().getFullYear().toString(),
    semesterRegisterOpenDate: new Date().toISOString().split('T')[0], // format as YYYY-MM-DD
  };

  let { 
    form = $bindable(),
    formInitial = formInitialValue
  }: {
    form: SemesterCreateRequest | undefined;
    formInitial: typeof formInitialValue;
  } = $props();

  let unprocessedForm = $derived(formInitial);
  
    $effect(() => {
    // Update the bound form when unprocessedForm changes
        if (unprocessedForm && unprocessedForm.semesterRegisterOpenDate) {
            form = { 
                ...unprocessedForm,
                semesterRegisterOpenDate: new Date(unprocessedForm.semesterRegisterOpenDate)
            };
        }
    });

</script>

<form>
    <section>
        <Textfield
            bind:value={unprocessedForm.semesterName}
            label="Semester Name"
            required
        />
        <Select bind:value={unprocessedForm.semesterType} label="Semester Type" required>
            <Option value="WINTER">Winter</Option>
            <Option value="SUMMER">Summer</Option>
        </Select>
    </section>
    <section>
        <Textfield
            type="number"
            bind:value={unprocessedForm.year}
            label="Year"
            required
        />
        <Textfield
            type="date"
            bind:value={unprocessedForm.semesterRegisterOpenDate}
            label="Register Open Date"
            required
        />
    </section>
</form>

<style>
  form {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
    max-width: 600px;
    margin: 0 auto;
    padding-top: 1rem;
  }
  form section {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 1rem;
  }
</style>