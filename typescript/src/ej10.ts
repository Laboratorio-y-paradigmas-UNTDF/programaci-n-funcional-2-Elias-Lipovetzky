// Ejercicio 10 — Result y validación encadenada (7 pts)
// Trazabilidad: F-19, F-20, F-21

export type FormData = { name: string; email: string; password: string };
export type Result<T, E> = { status: "ok"; value: T } | { status: "error"; error: E };
export type Validator<T> = (data: T) => Result<T, string>;

export function ok<T>(value: T): Result<T, string> {
  return { status: "ok", value };
}

export function err<T>(error: string): Result<T, string> {
  return { status: "error", error };
}

// Si result es error, propaga. Si ok, aplica validator al valor.
export function chain<T>(result: Result<T, string>, validator: Validator<T>): Result<T, string> {
  return result.status === "error" ? result : validator(result.value)
}

// Encadena: nombre requerido, email válido (tiene @ y .), password >= 8 chars.
export function validateForm(data: FormData): Result<FormData, string> {
  const nameValidator: Validator<FormData> = (data: FormData) => data.name === "" ? err<FormData>("nombre requerido") : ok<FormData>(data);
  const emailValidator: Validator<FormData> = (data: FormData) => !(data.email.includes("@") && data.email.includes(".")) ? err<FormData>("email inválido") : ok<FormData>(data);
  const passwordValidator: Validator<FormData> = (data: FormData) => !(data.password.length >= 8) ? err<FormData>("contraseña muy corta") : ok<FormData>(data);

  const validators: Validator<FormData>[] = [nameValidator, emailValidator, passwordValidator]
  return validators.reduce((acc, v) => chain(acc, v), ok<FormData>(data))
}

// 400 + error si falla, 200 + user si ok.
export function handleResult(result: Result<FormData, string>): { status: number; body: unknown } {
  return result.status === "error" ?  { status: 400, body: { error: result.error } } : { status: 200, body: {user: result.value} };
}
