export interface TokenPayload {
  id: number;
  sub: string;
  iat?: number;
  exp?: number;
}
