import{u as Z,c as $,d as ee,e as te,f as le,g as ne,h as ie,i as Y,a as Q,Q as w,b as ae}from"./QCard.6cbf04f5.js";import{a as ue,c as oe,Q as T}from"./use-router-link.71de0888.js";import{R as re,Q as se}from"./QBtn.c4e4055b.js";import{u as ce,a as de}from"./use-dark.a1c2acc3.js";import{c as X,f as me,e as fe}from"./render.a5d67b38.js";import{c,h as g,F as L,g as K,r as E,C as ve,Y as ge,U as be,G as he,s as pe,t as xe,u as B,v,L as ye}from"./index.14da1ee2.js";import{h as Se}from"./format.8f2d0f18.js";import{Q as Ve}from"./QPage.c53e4d6d.js";import{_ as Fe}from"./plugin-vue_export-helper.21dcd24c.js";const Ce={xs:8,sm:10,md:14,lg:20,xl:24};var ke=X({name:"QChip",props:{...ce,...ue,dense:Boolean,icon:String,iconRight:String,iconRemove:String,iconSelected:String,label:[String,Number],color:String,textColor:String,modelValue:{type:Boolean,default:!0},selected:{type:Boolean,default:null},square:Boolean,outline:Boolean,clickable:Boolean,removable:Boolean,removeAriaLabel:String,tabindex:[String,Number],disable:Boolean,ripple:{type:[Boolean,Object],default:!0}},emits:["update:modelValue","update:selected","remove","click"],setup(e,{slots:i,emit:p}){const{proxy:{$q:b}}=K(),l=de(e,b),d=oe(e,Ce),r=c(()=>e.selected===!0||e.icon!==void 0),S=c(()=>e.selected===!0?e.iconSelected||b.iconSet.chip.selected:e.icon),q=c(()=>e.iconRemove||b.iconSet.chip.remove),V=c(()=>e.disable===!1&&(e.clickable===!0||e.selected!==null)),_=c(()=>{const a=e.outline===!0&&e.color||e.textColor;return"q-chip row inline no-wrap items-center"+(e.outline===!1&&e.color!==void 0?` bg-${e.color}`:"")+(a?` text-${a} q-chip--colored`:"")+(e.disable===!0?" disabled":"")+(e.dense===!0?" q-chip--dense":"")+(e.outline===!0?" q-chip--outline":"")+(e.selected===!0?" q-chip--selected":"")+(V.value===!0?" q-chip--clickable cursor-pointer non-selectable q-hoverable":"")+(e.square===!0?" q-chip--square":"")+(l.value===!0?" q-chip--dark q-dark":"")}),C=c(()=>{const a=e.disable===!0?{tabindex:-1,"aria-disabled":"true"}:{tabindex:e.tabindex||0},k={...a,role:"button","aria-hidden":"false","aria-label":e.removeAriaLabel||b.lang.label.remove};return{chip:a,remove:k}});function z(a){a.keyCode===13&&P(a)}function P(a){e.disable||(p("update:selected",!e.selected),p("click",a))}function N(a){(a.keyCode===void 0||a.keyCode===13)&&(L(a),e.disable===!1&&(p("update:modelValue",!1),p("remove")))}function s(){const a=[];V.value===!0&&a.push(g("div",{class:"q-focus-helper"})),r.value===!0&&a.push(g(T,{class:"q-chip__icon q-chip__icon--left",name:S.value}));const k=e.label!==void 0?[g("div",{class:"ellipsis"},[e.label])]:void 0;return a.push(g("div",{class:"q-chip__content col row no-wrap items-center q-anchor--skip"},fe(i.default,k))),e.iconRight&&a.push(g(T,{class:"q-chip__icon q-chip__icon--right",name:e.iconRight})),e.removable===!0&&a.push(g(T,{class:"q-chip__icon q-chip__icon--remove cursor-pointer",name:q.value,...C.value.remove,onClick:N,onKeyup:N})),a}return()=>{if(e.modelValue===!1)return;const a={class:_.value,style:d.value};return V.value===!0&&Object.assign(a,C.value.chip,{onClick:P,onKeyup:z}),me("div",a,s(),"ripple",e.ripple!==!1&&e.disable!==!0,()=>[[re,e.ripple]])}}});function D(e,i,p,b){const l=[];return e.forEach(d=>{b(d)===!0?l.push(d):i.push({failedPropValidation:p,file:d})}),l}function R(e){e&&e.dataTransfer&&(e.dataTransfer.dropEffect="copy"),L(e)}const qe={multiple:Boolean,accept:String,capture:String,maxFileSize:[Number,String],maxTotalSize:[Number,String],maxFiles:[Number,String],filter:Function},_e=["rejected"];function ze({editable:e,dnd:i,getFileInput:p,addFilesToQueue:b}){const{props:l,emit:d,proxy:r}=K(),S=E(null),q=c(()=>l.accept!==void 0?l.accept.split(",").map(t=>(t=t.trim(),t==="*"?"*/":(t.endsWith("/*")&&(t=t.slice(0,t.length-1)),t.toUpperCase()))):null),V=c(()=>parseInt(l.maxFiles,10)),_=c(()=>parseInt(l.maxTotalSize,10));function C(t){if(e.value)if(t!==Object(t)&&(t={target:null}),t.target!==null&&t.target.matches('input[type="file"]')===!0)t.clientX===0&&t.clientY===0&&ve(t);else{const x=p();x&&x!==t.target&&x.click(t)}}function z(t){e.value&&t&&b(null,t)}function P(t,x,j,I){let u=Array.from(x||t.target.files);const h=[],F=()=>{h.length!==0&&d("rejected",h)};if(l.accept!==void 0&&q.value.indexOf("*/")===-1&&(u=D(u,h,"accept",o=>q.value.some(m=>o.type.toUpperCase().startsWith(m)||o.name.toUpperCase().endsWith(m))),u.length===0))return F();if(l.maxFileSize!==void 0){const o=parseInt(l.maxFileSize,10);if(u=D(u,h,"max-file-size",m=>m.size<=o),u.length===0)return F()}if(l.multiple!==!0&&u.length!==0&&(u=[u[0]]),u.forEach(o=>{o.__key=o.webkitRelativePath+o.lastModified+o.name+o.size}),I===!0){const o=j.map(m=>m.__key);u=D(u,h,"duplicate",m=>o.includes(m.__key)===!1)}if(u.length===0)return F();if(l.maxTotalSize!==void 0){let o=I===!0?j.reduce((m,O)=>m+O.size,0):0;if(u=D(u,h,"max-total-size",m=>(o+=m.size,o<=_.value)),u.length===0)return F()}if(typeof l.filter=="function"){const o=l.filter(u);u=D(u,h,"filter",m=>o.includes(m))}if(l.maxFiles!==void 0){let o=I===!0?j.length:0;if(u=D(u,h,"max-files",()=>(o++,o<=V.value)),u.length===0)return F()}if(F(),u.length!==0)return u}function N(t){R(t),i.value!==!0&&(i.value=!0)}function s(t){L(t),(t.relatedTarget!==null||ge.is.safari!==!0?t.relatedTarget!==S.value:document.elementsFromPoint(t.clientX,t.clientY).includes(S.value)===!1)===!0&&(i.value=!1)}function a(t){R(t);const x=t.dataTransfer.files;x.length!==0&&b(null,x),i.value=!1}function k(t){if(i.value===!0)return g("div",{ref:S,class:`q-${t}__dnd absolute-full`,onDragenter:R,onDragover:R,onDragleave:s,onDrop:a})}return Object.assign(r,{pickFiles:C,addFiles:z}),{pickFiles:C,addFiles:z,onDragover:N,onDragleave:s,processFiles:P,getDndNode:k,maxFilesNumber:V,maxTotalSizeNumber:_}}var Pe=X({name:"QFile",inheritAttrs:!1,props:{...Z,...$,...qe,modelValue:[File,FileList,Array],append:Boolean,useChips:Boolean,displayValue:[String,Number],tabindex:{type:[String,Number],default:0},counterLabel:Function,inputClass:[Array,String,Object],inputStyle:[Array,String,Object]},emits:[...ee,..._e],setup(e,{slots:i,emit:p,attrs:b}){const{proxy:l}=K(),d=te(),r=E(null),S=E(!1),q=le(e),{pickFiles:V,onDragover:_,onDragleave:C,processFiles:z,getDndNode:P}=ze({editable:d.editable,dnd:S,getFileInput:M,addFilesToQueue:W}),N=ne(e),s=c(()=>Object(e.modelValue)===e.modelValue?"length"in e.modelValue?Array.from(e.modelValue):[e.modelValue]:[]),a=c(()=>Y(s.value)),k=c(()=>s.value.map(n=>n.name).join(", ")),t=c(()=>Se(s.value.reduce((n,f)=>n+f.size,0))),x=c(()=>({totalSize:t.value,filesNumber:s.value.length,maxFiles:e.maxFiles})),j=c(()=>({tabindex:-1,type:"file",title:"",accept:e.accept,capture:e.capture,name:q.value,...b,id:d.targetUid.value,disabled:d.editable.value!==!0})),I=c(()=>"q-file q-field--auto-height"+(S.value===!0?" q-file--dnd":"")),u=c(()=>e.multiple===!0&&e.append===!0);function h(n){const f=s.value.slice();f.splice(n,1),o(f)}function F(n){const f=s.value.indexOf(n);f!==-1&&h(f)}function o(n){p("update:modelValue",e.multiple===!0?n:n[0])}function m(n){n.keyCode===13&&he(n)}function O(n){(n.keyCode===13||n.keyCode===32)&&V(n)}function M(){return r.value}function W(n,f){const y=z(n,f,s.value,u.value),U=M();U!=null&&(U.value=""),y!==void 0&&((e.multiple===!0?e.modelValue&&y.every(J=>s.value.includes(J)):e.modelValue===y[0])||o(u.value===!0?s.value.concat(y):y))}function A(){return[g("input",{class:[e.inputClass,"q-file__filler"],style:e.inputStyle})]}function G(){if(i.file!==void 0)return s.value.length===0?A():s.value.map((f,y)=>i.file({index:y,file:f,ref:this}));if(i.selected!==void 0)return s.value.length===0?A():i.selected({files:s.value,ref:this});if(e.useChips===!0)return s.value.length===0?A():s.value.map((f,y)=>g(ke,{key:"file-"+y,removable:d.editable.value,dense:!0,textColor:e.color,tabindex:e.tabindex,onRemove:()=>{h(y)}},()=>g("span",{class:"ellipsis",textContent:f.name})));const n=e.displayValue!==void 0?e.displayValue:k.value;return n.length!==0?[g("div",{class:e.inputClass,style:e.inputStyle,textContent:n})]:A()}function H(){const n={ref:r,...j.value,...N.value,class:"q-field__input fit absolute-full cursor-pointer",onChange:W};return e.multiple===!0&&(n.multiple=!0),g("input",n)}return Object.assign(d,{fieldClass:I,emitValue:o,hasValue:a,inputRef:r,innerValue:s,floatingLabel:c(()=>a.value===!0||Y(e.displayValue)),computedCounter:c(()=>{if(e.counterLabel!==void 0)return e.counterLabel(x.value);const n=e.maxFiles;return`${s.value.length}${n!==void 0?" / "+n:""} (${t.value})`}),getControlChild:()=>P("file"),getControl:()=>{const n={ref:d.targetRef,class:"q-field__native row items-center cursor-pointer",tabindex:e.tabindex};return d.editable.value===!0&&Object.assign(n,{onDragover:_,onDragleave:C,onKeydown:m,onKeyup:O}),g("div",n,[H()].concat(G()))}}),Object.assign(l,{removeAtIndex:h,removeFile:F,getNativeElement:()=>r.value}),be(l,"nativeEl",()=>r.value),ie(d)}});const Ne={data(){return{name:"",email:"",password:"",confirmPassword:"",introduction:"",phoneNumber:"",profilePicture:null}},methods:{signup(){this.name,this.email}}},we=ye("div",{class:"text-h6"},"\uD68C\uC6D0\uAC00\uC785",-1);function Be(e,i,p,b,l,d){return pe(),xe(Ve,{class:"flex flex-center"},{default:B(()=>[v(ae,{class:"q-pa-md",style:{width:"400px"}},{default:B(()=>[v(Q,null,{default:B(()=>[we]),_:1}),v(Q,null,{default:B(()=>[v(w,{outlined:"",modelValue:l.name,"onUpdate:modelValue":i[0]||(i[0]=r=>l.name=r),label:"\uC774\uB984"},null,8,["modelValue"]),v(w,{outlined:"",modelValue:l.email,"onUpdate:modelValue":i[1]||(i[1]=r=>l.email=r),label:"\uC774\uBA54\uC77C"},null,8,["modelValue"]),v(w,{outlined:"",type:"password",modelValue:l.password,"onUpdate:modelValue":i[2]||(i[2]=r=>l.password=r),label:"\uBE44\uBC00\uBC88\uD638"},null,8,["modelValue"]),v(w,{outlined:"",type:"password",modelValue:l.confirmPassword,"onUpdate:modelValue":i[3]||(i[3]=r=>l.confirmPassword=r),label:"\uBE44\uBC00\uBC88\uD638 \uD655\uC778"},null,8,["modelValue"]),v(w,{outlined:"",modelValue:l.introduction,"onUpdate:modelValue":i[4]||(i[4]=r=>l.introduction=r),label:"\uC790\uAE30\uC18C\uAC1C"},null,8,["modelValue"]),v(w,{outlined:"",modelValue:l.phoneNumber,"onUpdate:modelValue":i[5]||(i[5]=r=>l.phoneNumber=r),label:"\uD734\uB300\uD3F0 \uBC88\uD638"},null,8,["modelValue"])]),_:1}),v(Q,{class:"q-pt-sm"},{default:B(()=>[v(Pe,{modelValue:l.profilePicture,"onUpdate:modelValue":i[6]||(i[6]=r=>l.profilePicture=r),label:"\uD504\uB85C\uD544 \uC0AC\uC9C4",accept:"image/*"},null,8,["modelValue"])]),_:1}),v(Q,{class:"text-right"},{default:B(()=>[v(se,{label:"\uAC00\uC785\uD558\uAE30",color:"primary",onClick:d.signup},null,8,["onClick"])]),_:1})]),_:1})]),_:1})}var Ee=Fe(Ne,[["render",Be]]);export{Ee as default};