import React from 'react';

const Test: React.FC = () => {
    return (
        <div>
         <div className="avatar">
  <div className="w-32 rounded">
    <img src="https://img.daisyui.com/images/profile/demo/superperson@192.webp" />
  </div>
</div>
<div className="avatar">
  <div className="w-20 rounded">
    <img
      src="https://img.daisyui.com/images/profile/demo/superperson@192.webp"
      alt="Tailwind-CSS-Avatar-component"
    />
  </div>
</div>
<div className="avatar">
  <div className="w-16 rounded">
    <img
      src="https://img.daisyui.com/images/profile/demo/superperson@192.webp"
      alt="Tailwind-CSS-Avatar-component"
    />
  </div>
</div>
<div className="avatar">
  <div className="w-8 rounded">
    <img
      src="https://img.daisyui.com/images/profile/demo/superperson@192.webp"
      alt="Tailwind-CSS-Avatar-component"
    />
  </div>
</div>
<details className="dropdown">
            <summary className="btn m-1">open or close</summary>
            <ul className="menu dropdown-content bg-base-100 rounded-box z-1 w-52 p-2 shadow-sm">
                <li><a>Item 1</a></li>
                <li><a>Item 2</a></li>
            </ul>
        </details>
        </div>
      
    );
}

export default Test;