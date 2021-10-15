import { registerPlugin } from '@capacitor/core';

import type { cleoirPlugin } from './definitions';

const cleoir = registerPlugin<cleoirPlugin>('cleoir', {
  web: () => import('./web').then(m => new m.cleoirWeb()),
});

export * from './definitions';
export { cleoir };
