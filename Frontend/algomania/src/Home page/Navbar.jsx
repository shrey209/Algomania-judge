import { useState } from 'react';
import { Fragment } from 'react';
import { Disclosure } from '@headlessui/react';
import { Bars3Icon, XMarkIcon } from '@heroicons/react/24/outline';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/context'; // Import the useAuth hook
import logo from '../images/logo.png'; // Import the logo
import SignInModal from '../SignIn/SignInModal';

const navigation = [
  { name: 'Algomania', href: '#', current: true },
  { name: 'Explore', href: '/Explore', current: false },
  { name: 'Problems', href: '/Problems', current: false },
  { name: 'Contest', href: '/Contest', current: false },
  { name: 'Code Editor', href: '/Editor', current: false },
];

function classNames(...classes) {
  return classes.filter(Boolean).join(' ');
}

export default function Navbar() {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();
  const [isSignInOpen, setIsSignInOpen] = useState(false);

  return (
    <Disclosure as="nav" className="bg-white">
      {({ open }) => (
        <>
          <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
            <div className="relative flex h-16 items-center justify-between">
              <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
                {/* Mobile menu button */}
                <Disclosure.Button className="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-200 hover:text-black focus:outline-none focus:ring-2 focus:ring-inset focus:ring-black">
                  <span className="sr-only">Open main menu</span>
                  {open ? (
                    <XMarkIcon className="block h-6 w-6" aria-hidden="true" />
                  ) : (
                    <Bars3Icon className="block h-6 w-6" aria-hidden="true" />
                  )}
                </Disclosure.Button>
              </div>
              <div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
                <div className="flex flex-shrink-0 items-center">
                  <img className="w-16" src={logo} alt="Logo" />
                </div>
                <div className="hidden sm:ml-6 sm:block">
                  <div className="flex space-x-4">
                    {navigation.map((item) => (
                      <a
                        key={item.name}
                        href={item.href}
                        className={classNames(
                          item.current
                            ? 'bg-purple-500 text-white shadow-lg'
                            : 'text-gray-900 hover:bg-gray-200 hover:text-black shadow-lg',
                          'rounded-md px-3 py-2 text-base font-medium' // Increased font size to text-base
                        )}
                        aria-current={item.current ? 'page' : undefined}
                      >
                        {item.name}
                      </a>
                    ))}
                  </div>
                </div>
              </div>
              <div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
              {isAuthenticated ? (
  <button
    type="button"
    onClick={() => navigate('/Dashboard')}
    className="text-white bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-purple-300 dark:focus:ring-purple-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2 shadow-lg"
  >
    Dashboard
  </button>
) : (
  <button
    type="button"
    onClick={() => setIsSignInOpen(true)}
    className="text-white bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-purple-300 dark:focus:ring-purple-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2 shadow-lg"
  >
    Sign in
  </button>
)}
              </div>
            </div>
          </div>

          <SignInModal isOpen={isSignInOpen} onClose={() => setIsSignInOpen(false)} />

          <Disclosure.Panel className="sm:hidden">
            <div className="space-y-1 px-2 pb-3 pt-2">
              {navigation.map((item) => (
                <Disclosure.Button
                  key={item.name}
                  as="a"
                  href={item.href}
                  className={classNames(
                    item.current
                      ? 'bg-purple-500 text-white shadow-lg'
                      : 'text-gray-900 hover:bg-gray-200 hover:text-black shadow-lg',
                    'block rounded-md px-3 py-2 text-base font-medium' // Increased font size to text-base
                  )}
                  aria-current={item.current ? 'page' : undefined}
                >
                  {item.name}
                </Disclosure.Button>
              ))}
            </div>
          </Disclosure.Panel>
        </>
      )}
    </Disclosure>
  );
}
