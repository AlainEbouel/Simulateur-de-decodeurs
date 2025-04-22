import { Decoder } from './decoder.model';

export interface Client {
  id: number;
  name: string;
  decoders: Decoder[];
}
