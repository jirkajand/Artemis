<script module lang="ts">
  export type SemesterFormDirty = Omit<SemesterCreateRequest, 'semesterRegisterOpenDate'> & {
    semesterRegisterOpenDate: string;
  };
</script>

<script lang="ts">
    import Select, { Option } from "@smui/select";
    import Textfield from '@smui/textfield';
    import type { SemesterCreateRequest } from '$lib/api';

  let { 
    form = $bindable()
  }: {
    form: SemesterFormDirty;
  } = $props();

</script>

<form>
    <section>
        <Textfield
            bind:value={form.semesterName}
            label="Semester Name"
            required
        />
        <Select bind:value={form.semesterType} label="Semester Type" required>
            <Option value="WINTER">Winter</Option>
            <Option value="SUMMER">Summer</Option>
        </Select>
    </section>
    <section>
        <Textfield
            type="text"
            bind:value={form.year}
            label="Year"
            required
        />
        <Textfield
            type="date"
            bind:value={form.semesterRegisterOpenDate}
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