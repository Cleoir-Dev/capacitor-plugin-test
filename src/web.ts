import { WebPlugin } from '@capacitor/core';

import type { cleoirPlugin } from './definitions';

export class cleoirWeb extends WebPlugin implements cleoirPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
